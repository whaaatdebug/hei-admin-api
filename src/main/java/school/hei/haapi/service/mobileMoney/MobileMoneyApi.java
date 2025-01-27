package school.hei.haapi.service.mobileMoney;

import java.util.List;
import school.hei.haapi.endpoint.rest.model.MobileMoneyType;
import school.hei.haapi.http.model.TransactionDetails;
import school.hei.haapi.model.exception.ApiException;

public interface MobileMoneyApi {
  TransactionDetails getByTransactionRef(MobileMoneyType type, String ref) throws ApiException;

  List<TransactionDetails> fetchThenSaveTransactionsDetails(MobileMoneyType type);
}
