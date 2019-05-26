package clustercamp.user.service;

import clustercamp.base.dto.UserDTO;
import clustercamp.user.repository.UserRxRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserRxService {

  private final UserRxRepository repository;

  private final UserMapper mapper;

  @HystrixCommand(fallbackMethod = "detail_")
  public Mono<UserDTO> detail(String userId) {
    return repository.findByUserId(userId)
      .doOnError(HttpClientErrorException.class (),  ->new HttpClientErrorException(HttpStatus.NOT_FOUND))
      .map(mapper::convert);
  }

  public UserDTO detail_(String userId) {
    return new UserDTO();
  }

  @Transactional
  @HystrixCommand(fallbackMethod = "defaultFallback")
  public UserDTO create(UserDTO userDto) {
    var user = mapper.convert(userDto);
    return mapper.convert(repository.save(user));
  }

  @Transactional
  @HystrixCommand(fallbackMethod = "defaultFallback")
  public void remove(Long id) {
    repository.deleteById(id);
  }

  @Transactional
  @HystrixCommand(fallbackMethod = "defaultFallback")
  public UserDTO modify(Long id, UserDTO userDto) {
    return repository.findById(id)
      .map(c -> {
        c.setUserPass(userDto.getUserPass());
        return mapper.convert(repository.save(c));
      })
      .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
  }
}
