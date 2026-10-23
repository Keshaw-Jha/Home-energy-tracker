package com.leetjourney.device_service.service;

import com.leetjourney.device_service.dto.DeviceDto;
import com.leetjourney.device_service.entity.Device;
import com.leetjourney.device_service.exception.DeviceNotFoundException;
import com.leetjourney.device_service.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class DeviceService {

    private DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository){
        this.deviceRepository = deviceRepository;
    }

    public DeviceDto getDeviceDtoById(Long id) {
        return toDeviceDto(getDeviceById(id));
    }

    public Device getDeviceById(Long id) {
        return deviceRepository.findById(id)
                .orElseThrow(()-> new DeviceNotFoundException("Device not found with id: "+id));
    }

    public DeviceDto createDevice(DeviceDto deviceDto) {
        Device newDevice = deviceRepository.save(toDevice(deviceDto));
        return toDeviceDto(newDevice);
    }

    public DeviceDto updateDevice(Long id, DeviceDto deviceDto) {
        Device device = this.getDeviceById(id);
        device.setName(deviceDto.name());
        device.setType(deviceDto.type());
        device.setLocation(deviceDto.location());
        device.setUserId(deviceDto.userId());

        final Device updatedDevice = deviceRepository.save(device);
        return this.toDeviceDto(updatedDevice);
    }

    public void deleteDevice(Long id) {
        if(!deviceRepository.existsById(id)){
            throw new DeviceNotFoundException("Device not found with id: "+id);
        }
        deviceRepository.deleteById(id);
    }

    public List<DeviceDto> getAllDevicesByUserId(Long userId) {
        return deviceRepository.findByUserId(userId)
                .stream()
                .map(this::toDeviceDto)
                .toList();
    }

    public DeviceDto toDeviceDto(Device device){
            return DeviceDto.builder()
                    .id(device.getId())
                    .name(device.getName())
                    .type(device.getType())
                    .userId(device.getUserId())
                    .location(device.getLocation()).build();
    }

    public Device toDevice(DeviceDto deviceDto){
        return Device.builder()
                .name(deviceDto.name())
                .type(deviceDto.type())
                .userId(deviceDto.userId())
                .location(deviceDto.location()).build();
    }
}
