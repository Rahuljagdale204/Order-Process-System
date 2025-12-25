package com.mini.orderapp.service;

import com.mini.orderapp.domain.Orders;
import com.mini.orderapp.domain.User;
import com.mini.orderapp.repository.OrdersRepository;
import com.mini.orderapp.repository.UserRepository;
import com.mini.orderapp.security.SecurityUtils;
import com.mini.orderapp.service.customService.OrderFileWriter;
import com.mini.orderapp.service.dto.OrdersDTO;
import com.mini.orderapp.service.mapper.OrdersMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mini.orderapp.domain.Orders}.
 */
@Service
@Transactional
public class OrdersService {

    private static final Logger log = LoggerFactory.getLogger(OrdersService.class);

    private final OrdersRepository ordersRepository;

    private final OrdersMapper ordersMapper;

    private final OrderFileWriter orderFileWriter;

    private final UserRepository userRepository;

    public OrdersService(
        OrdersRepository ordersRepository,
        OrdersMapper ordersMapper,
        OrderFileWriter orderFileWriter,
        UserRepository userRepository
    ) {
        this.ordersRepository = ordersRepository;
        this.ordersMapper = ordersMapper;
        this.orderFileWriter = orderFileWriter;
        this.userRepository = userRepository;
    }

    /**
     * Save a orders.
     *
     * @param ordersDTO the entity to save.
     * @return the persisted entity.
     */
    public OrdersDTO save(OrdersDTO ordersDTO) {
        log.debug("Request to save Orders : {}", ordersDTO);
        Orders orders = ordersMapper.toEntity(ordersDTO);
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElseThrow()).orElseThrow();
        orders.setUser(user);
        orders = ordersRepository.save(orders);
        ordersDTO = ordersMapper.toDto(orders);
        orderFileWriter.writeOrder(ordersDTO);
        return ordersDTO;
    }

    /**
     * Update a orders.
     *
     * @param ordersDTO the entity to save.
     * @return the persisted entity.
     */
    public OrdersDTO update(OrdersDTO ordersDTO) {
        log.debug("Request to update Orders : {}", ordersDTO);
        Orders orders = ordersMapper.toEntity(ordersDTO);
        orders = ordersRepository.save(orders);
        return ordersMapper.toDto(orders);
    }

    /**
     * Partially update a orders.
     *
     * @param ordersDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrdersDTO> partialUpdate(OrdersDTO ordersDTO) {
        log.debug("Request to partially update Orders : {}", ordersDTO);

        return ordersRepository
            .findById(ordersDTO.getId())
            .map(existingOrders -> {
                ordersMapper.partialUpdate(existingOrders, ordersDTO);

                return existingOrders;
            })
            .map(ordersRepository::save)
            .map(ordersMapper::toDto);
    }

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        if (SecurityUtils.hasCurrentUserThisAuthority("ROLE_ADMIN")) {
            return ordersRepository.findAll(pageable).map(ordersMapper::toDto);
        }

        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElseThrow()).orElseThrow();
        if (user == null) return Page.empty();

        return ordersRepository.getOrdersByUser(user, pageable).map(ordersMapper::toDto);
    }

    /**
     * Get one orders by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrdersDTO> findOne(Long id) {
        log.debug("Request to get Orders : {}", id);
        return ordersRepository.findById(id).map(ordersMapper::toDto);
    }

    public List<OrdersDTO> findOrdersByCustomerId(String customerId) {
        log.debug("Request to get Orders By CustomerId: {}", customerId);
        List<Orders> orders = ordersRepository.getOrdersByCustomerId(customerId);
        return ordersMapper.toDto(orders);
    }

    /**
     * Delete the orders by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Orders : {}", id);
        ordersRepository.deleteById(id);
    }
}
