package se.lexicon.booking.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.booking.api.model.entity.Booking;
import se.lexicon.booking.api.repository.BookingRepository;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/booking/")
public class BookingController {

    @Autowired
    BookingRepository bookingRepository;


    @PostMapping()
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingRepository.save(booking));
    }

    @GetMapping()
    public ResponseEntity<List<Booking>> find(
            @RequestParam(value = "search", defaultValue = "all") String search,
            @RequestParam(value = "value", required = false) List<String> values
    ) {
        List<Booking> bookings;
        switch (search.toLowerCase()) {
            case "between":
                bookings = bookingRepository.findByDateTimeBetween(
                        LocalDateTime.parse(values.get(0)), LocalDateTime.parse(values.get(1))
                );
                break;
            case "before":
                bookings = bookingRepository.findByDateTimeBefore(LocalDateTime.parse(values.get(0)));
                break;
            case "after":
                bookings = bookingRepository.findByDateTimeAfter(LocalDateTime.parse(values.get(0)));
                break;
            case "administrator":
                bookings = bookingRepository.findByAdministratorId(values.get(0));
                break;
            case "vaccine":
                bookings = bookingRepository.findByVaccineType(values.get(0));
                break;
            case "vacant":
                bookings = bookingRepository.findByVacantStatus(Boolean.parseBoolean(values.get(0)));
                break;
            case "all":
                bookings = bookingRepository.findAll();
                break;
            default:
                throw new IllegalArgumentException("Invalid search value: " + search + ". Valid search values are 'all', 'between' 'before', 'after', 'administrator', 'vaccine', 'vacant' and 'city'.");
        }

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("{id}")
    public ResponseEntity<Booking> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok(bookingRepository.findById(id).orElse(null));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        bookingRepository.delete(bookingRepository.findById(id).orElseThrow(IllegalAccessError::new));
        return ResponseEntity.noContent().build();
    }
}
