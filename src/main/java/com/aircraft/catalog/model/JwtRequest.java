package com.aircraft.catalog.model;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest implements Serializable {
  private static final long serialVersionUID = 5926468583005150707L;

  @NotBlank
  private String username;

  @NotBlank
  private String password;
}
