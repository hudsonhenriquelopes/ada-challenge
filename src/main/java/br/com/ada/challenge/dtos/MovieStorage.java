package br.com.ada.challenge.dtos;

import br.com.ada.challenge.dtos.MovieDto;

import java.util.List;

public record MovieStorage(List<MovieDto> movies) {
}
