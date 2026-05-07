package com.harshi_solution.order.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.harshi_solution.order.entities.DeliveryNote;
import com.harshi_solution.order.entities.DeliveryNote.Status;

@Repository
public interface DeliveryNoteRepository extends JpaRepository<DeliveryNote, Long> {

    List<DeliveryNote> findByOrderId(Long orderId);

    List<DeliveryNote> findByWareId(Long wareId);

    List<DeliveryNote> findByWareIdAndStatus(Long wareId, Status status);

    List<DeliveryNote> findByOrderIdAndWareId(Long orderId, Long wareId);

    List<DeliveryNote> findByStatus(Status status);

    // check if all notes for an order are dispatched
    @Query("""
        SELECT COUNT(d) = 0
        FROM DeliveryNote d
        WHERE d.orderId = :orderId
        AND d.status = 'PENDING'
    """)
    boolean allDispatchedForOrder(Long orderId);

    // check if all notes for an order are delivered
    @Query("""
        SELECT COUNT(d) = 0
        FROM DeliveryNote d
        WHERE d.orderId = :orderId
        AND d.status != 'DELIVERED'
    """)
    boolean allDeliveredForOrder(Long orderId);

    long countByOrderIdAndStatus(Long orderId, Status status);
}