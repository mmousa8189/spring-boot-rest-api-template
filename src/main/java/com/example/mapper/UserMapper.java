package com.example.mapper;

import com.example.model.dto.UserDto;
import com.example.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper for the entity User and its DTO UserDto.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convert entity to DTO.
     *
     * @param user the entity
     * @return the DTO
     */
    UserDto toDto(User user);

    /**
     * Convert DTO to entity.
     *
     * @param userDto the DTO
     * @return the entity
     */
    User toEntity(UserDto userDto);

    /**
     * Update entity from DTO.
     *
     * @param userDto the DTO
     * @param user the entity to update
     * @return the updated entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User updateEntityFromDto(UserDto userDto, @MappingTarget User user);
}
