package com.harshi_solution.transport.service;

import org.springframework.data.domain.Page;

import com.harshi_solution.transport.dto.TransportRequestDTO;
import com.harshi_solution.transport.dto.TransportResponseDTO;

public interface TransportService {

	Page<TransportResponseDTO> getAllTransports(int page, int size);

	TransportResponseDTO getTransportById(Long selectedTransportId);

	TransportResponseDTO saveTransport(TransportRequestDTO request);

	TransportResponseDTO updateTransport(Long id, TransportRequestDTO request);

	void deleteTransportById(Long transportId);

}
