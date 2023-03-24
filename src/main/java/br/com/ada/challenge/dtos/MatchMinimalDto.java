package br.com.ada.challenge.dtos;

import java.io.Serializable;

public record MatchMinimalDto(String username, int points, int errors) implements Serializable {
}
