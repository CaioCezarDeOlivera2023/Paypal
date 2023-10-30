package modelService;

import java.time.LocalDate;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {
	
	private OnlinePaymentService onlinePaymentService;
	
	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}
	
	public void processContract(Contract contract, int months) {
		
		double basciQuota = contract.getTotalValue() / months;
		
		for(int i=1; i <= months; i++) {
			
			LocalDate dueDate = contract.getDate().plusMonths(i);
			
			double interes = onlinePaymentService.interest(basciQuota, i); 
			double fee = onlinePaymentService.paymentFee(basciQuota+ interes);
			double quota = basciQuota + interes + fee;
			
			contract.getInstallments().add(new Installment(dueDate, quota));
			
		}
		
	}

}
