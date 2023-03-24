package br.com.ada.challenge.dtos;

import java.io.Serializable;
import java.util.List;

public record MoviePairStorage(List<MoviePairDto> pairs)
        implements Serializable {
}
