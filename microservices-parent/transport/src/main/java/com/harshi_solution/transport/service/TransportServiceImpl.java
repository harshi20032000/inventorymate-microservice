package com.harshi_solution.transport.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harshi_solution.transport.dto.TransportRequestDTO;
import com.harshi_solution.transport.dto.TransportResponseDTO;
import com.harshi_solution.transport.dto.VehicleResponseDTO;
import com.harshi_solution.transport.entities.Transport;
import com.harshi_solution.transport.entities.TransportAndBuiltNumber;
import com.harshi_solution.transport.repo.TransportRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TransportServiceImpl implements TransportService {

	@Autowired
	private TransportRepository transportRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<TransportResponseDTO> getAllTransports(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("transportId").descending());
		Page<Transport> transportPage = transportRepository.findAll(pageable);
		return transportPage.map(this::mapToResponseDTO);
	}

	@Override
	@Transactional(readOnly = true)
	public TransportResponseDTO getTransportById(Long transportId) {

		Transport transport = transportRepository.findById(transportId)
				.orElseThrow(() -> new EntityNotFoundException("Transport not found with id: " + transportId));

		return mapToResponseDTO(transport);
	}

	@Override
	@Transactional
	public TransportResponseDTO saveTransport(TransportRequestDTO request) {

		Transport transport = new Transport();
		transport.setTransportName(request.getTransportName());
		transport.setContactDetails(request.getContactDetails());
		transport.setCreatedAt(Instant.now());
		transport.setUpdatedAt(Instant.now());

		List<TransportAndBuiltNumber> vehicles = request.getVehicles()
				.stream()
				.map(vehicleDTO -> {
					TransportAndBuiltNumber vehicle = new TransportAndBuiltNumber();
					vehicle.setBuiltNumber(vehicleDTO.getBuiltNumber());
					vehicle.setDriverName(vehicleDTO.getDriverName());
					vehicle.setDriverContact(vehicleDTO.getDriverContact());
					vehicle.setCreatedAt(Instant.now());
					vehicle.setUpdatedAt(Instant.now());
					vehicle.setTransport(transport);
					return vehicle;
				})
				.toList();

		transport.setVehicles(vehicles);

		Transport savedTransport = transportRepository.save(transport);

		return mapToResponseDTO(savedTransport);
	}

	@Override
	@Transactional
	public TransportResponseDTO updateTransport(Long id,
			TransportRequestDTO request) {

		Transport existingTransport = transportRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Transport not found with id: " + id));

		// Update simple fields
		if (request.getTransportName() != null) {
			existingTransport.setTransportName(request.getTransportName());
		}

		if (request.getContactDetails() != null) {
			existingTransport.setContactDetails(request.getContactDetails());
		}

		// Replace vehicles completely
		if (request.getVehicles() != null) {

			existingTransport.getVehicles().clear(); // orphanRemoval deletes old ones

			List<TransportAndBuiltNumber> newVehicles = request.getVehicles().stream()
					.map(vehicleDTO -> {
						TransportAndBuiltNumber vehicle = new TransportAndBuiltNumber();

						vehicle.setBuiltNumber(vehicleDTO.getBuiltNumber());
						vehicle.setDriverName(vehicleDTO.getDriverName());
						vehicle.setDriverContact(vehicleDTO.getDriverContact());
						vehicle.setCreatedAt(Instant.now());
						vehicle.setUpdatedAt(Instant.now());
						vehicle.setTransport(existingTransport);

						return vehicle;
					})
					.toList();

			existingTransport.getVehicles().addAll(newVehicles);
		}

		existingTransport.setUpdatedAt(Instant.now());

		Transport saved = transportRepository.save(existingTransport);

		return mapToResponseDTO(saved);
	}

	@Override
	@Transactional
	public void deleteTransportById(Long transportId) {

		Transport transport = transportRepository.findById(transportId)
				.orElseThrow(() -> new EntityNotFoundException(
						"Transport not found with id: " + transportId));

		transportRepository.delete(transport);
	}

	private TransportResponseDTO mapToResponseDTO(Transport transport) {

		List<VehicleResponseDTO> vehicles = transport.getVehicles()
				.stream()
				.map(vehicle -> {
					VehicleResponseDTO dto = new VehicleResponseDTO();
					dto.setId(vehicle.getId());
					dto.setBuiltNumber(vehicle.getBuiltNumber());
					dto.setDriverName(vehicle.getDriverName());
					dto.setDriverContact(vehicle.getDriverContact());
					dto.setCreatedAt(vehicle.getCreatedAt());
					dto.setUpdatedAt(vehicle.getUpdatedAt());
					return dto;
				})
				.toList();

		TransportResponseDTO dto = new TransportResponseDTO();
		dto.setTransportId(transport.getTransportId());
		dto.setTransportName(transport.getTransportName());
		dto.setContactDetails(transport.getContactDetails());
		dto.setVehicles(vehicles);
		dto.setCreatedAt(transport.getCreatedAt());
		dto.setUpdatedAt(transport.getUpdatedAt());

		return dto;
	}

}
