package org.vfl.vintago.mapper;

import org.vfl.vintago.dto.AddressDTO;
import org.vfl.vintago.dto.RouteAddressDTO;
import org.vfl.vintago.dto.RouteDTO;
import org.vfl.vintago.entity.Address;
import org.vfl.vintago.entity.DeliveryTruck;
import org.vfl.vintago.entity.Route;
import org.vfl.vintago.entity.RouteAddress;

import java.util.List;
import java.util.stream.Collectors;

public class RouteMapper {
    public static RouteDTO toRouteDTO(Route route) {
        List<RouteAddressDTO> routeAddresses = route.getRouteAddresses().stream()
                .map(RouteMapper::mapRouteAddress)
                .collect(Collectors.toList());

        DeliveryTruck deliveryTruck = route.getDeliveryTruck();

        return new RouteDTO(
                route.getId(),
                route.getDeliveryDate(),
                deliveryTruck.getName(),
                routeAddresses
        );
    }
    private static RouteAddressDTO mapRouteAddress(RouteAddress routeAddress) {
        Address address = routeAddress.getAddress();
        AddressDTO addressDto = new AddressDTO(
                address.getId(),
                address.getStreet(),
                address.getNumber(),
                address.getZip(),
                address.getCity(),
                address.getLocation()
        );

        return new RouteAddressDTO(
                routeAddress.getStepOrder(),
                routeAddress.getStatus(),
                addressDto
        );

    }

    public static List<RouteDTO> toRouteDTOList(List<Route> routes) {
        return routes.stream()
                .map(RouteMapper::toRouteDTO)
                .collect(Collectors.toList());
    }
}
