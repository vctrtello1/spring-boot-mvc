package com.victortello.ws.webservice.ui.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.victortello.ws.webservice.exceptions.UserServiceException;
import com.victortello.ws.webservice.model.request.UserDetailsRequestModel;
import com.victortello.ws.webservice.model.response.AddressesRest;
import com.victortello.ws.webservice.model.response.ErrorMessages;
import com.victortello.ws.webservice.model.response.OperationStatusModel;
import com.victortello.ws.webservice.model.response.RequestOperationStatus;
import com.victortello.ws.webservice.model.response.UserRest;
import com.victortello.ws.webservice.service.AddressService;
import com.victortello.ws.webservice.service.UserService;
import com.victortello.ws.webservice.shared.dto.AddressDTO;
import com.victortello.ws.webservice.shared.dto.UserDto;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class userController {

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressesService;

    @Autowired
    AddressService addressService;

    @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public UserRest getUser(@PathVariable String id) {

        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {

        UserRest returnValue = new UserRest();

        if (userDetails.getFirstName().isEmpty())
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto createdUser = userService.createUser(userDto);
        returnValue = modelMapper.map(createdUser, UserRest.class);
        return returnValue;
    }

    @PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE })
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();

        if (userDetails.getFirstName().isEmpty())
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        UserDto updatedUser = userService.updateUser(id, userDto);
        BeanUtils.copyProperties(updatedUser, returnValue);
        return returnValue;
    }

    @DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public OperationStatusModel deleteUser(@PathVariable String id) {

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<UserRest> returnValue = new ArrayList<>();

        List<UserDto> users = userService.getUsers(page, limit);

        for (UserDto userDto : users) {
            UserRest userModel = new UserRest();
            BeanUtils.copyProperties(userDto, userModel);
            returnValue.add(userModel);
        }

        return returnValue;
    }

    @GetMapping(path = "/{userId}/addresses", produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public CollectionModel<AddressesRest> getUserAddresses(@PathVariable String userId) {

        List<AddressesRest> returnValue = new ArrayList<>();

        List<AddressDTO> addressesDto = addressesService.getAddresses(userId);

        if (addressesDto != null && !addressesDto.isEmpty()) {
            Type listType = new TypeToken<List<AddressesRest>>() {
            }.getType();
            returnValue = new ModelMapper().map(addressesDto, listType);
        }

        for (AddressesRest addressRest : returnValue) {

            Link addressLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(userController.class).getUserAddress(userId, addressRest.getAddressId()))
                    .withSelfRel();
            addressRest.add(addressLink);
        }

        Link userLink = WebMvcLinkBuilder.linkTo(userController.class).slash(userId).withRel("user");
        Link addressesLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(userController.class).getUserAddresses(userId)).withSelfRel();
        return CollectionModel.of(returnValue, userLink, addressesLink);
    }

    @GetMapping(path = "/{userId}/addresses/{addressId}", produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public EntityModel<AddressesRest> getUserAddress(@PathVariable String userId, @PathVariable String addressId) {

        AddressDTO addressesDto = addressService.getAddress(addressId);

        Type listType = new TypeToken<AddressesRest>() {
        }.getType();
        AddressesRest returnValue = new ModelMapper().map(addressesDto, listType);

        Link userLink = WebMvcLinkBuilder.linkTo(userController.class).slash(userId).withRel("user");
        Link addressLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(userController.class).getUserAddress(userId, addressId))
                .withSelfRel();
        Link addressesLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(userController.class).getUserAddresses(userId)).withRel("addresses");

        return EntityModel.of(returnValue, Arrays.asList(userLink, addressLink, addressesLink));
    }

}
