package com.pradiptakalita.dto.actor;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorPageResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private List<ActorResponseDTO> actors;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private boolean hasNext;
    private boolean hasPrevious;
}
