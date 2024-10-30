package school.hei.haapi.service;

import static java.time.Instant.now;
import static java.util.stream.Collectors.toList;
import static school.hei.haapi.endpoint.rest.model.MpbsStatus.PENDING;

import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import school.hei.haapi.endpoint.event.EventProducer;
import school.hei.haapi.endpoint.event.model.PendingMpbsCheckRequested;
import school.hei.haapi.endpoint.event.model.PojaEvent;
import school.hei.haapi.model.Mpbs.Mpbs;
import school.hei.haapi.model.Mpbs.MpbsVerification;
import school.hei.haapi.repository.MpbsRepository;
import school.hei.haapi.repository.MpbsVerificationRepository;

@Service
@AllArgsConstructor
@Slf4j
public class MpbsVerificationService {
  private final MpbsVerificationRepository repository;
  private final MpbsRepository mpbsRepository;
  private final MobilePaymentService mobilePaymentService;
  private final EventProducer<PojaEvent> eventProducer;

  public List<MpbsVerification> findAllByStudentIdAndFeeId(String studentId, String feeId) {
    return repository.findAllByStudentIdAndFeeId(studentId, feeId);
  }

  public void firePendingMpbsCheckEvents() {
    List<Mpbs> pendingMpbs = mpbsRepository.findAllByStatus(PENDING);
    Instant verifyAt = now();
    log.info("pending mpbs = {} at {}", pendingMpbs.size(), verifyAt);
    eventProducer.accept(
        pendingMpbs.stream()
            .map((pending) -> this.toPendingMpbsCheckRequested(pending, verifyAt))
            .collect(toList()));
  }

  private PendingMpbsCheckRequested toPendingMpbsCheckRequested(Mpbs mpbs, Instant verifyAt) {
    return new PendingMpbsCheckRequested(mpbs, verifyAt);
  }

  public void fetchThenSaveTransactionDetailsDaily() {
    mobilePaymentService.fetchThenSaveTransactionDetails();
  }
}
