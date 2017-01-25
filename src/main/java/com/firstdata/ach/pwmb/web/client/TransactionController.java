package com.firstdata.ach.pwmb.web.client;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.firstdata.ach.pwmb.web.client.util.JsonUtil;
import com.firstdata.ach.pwmb.web.client.util.PropertyGetter;
import com.firstdata.payeezy.JSONHelper;
import com.firstdata.payeezy.PayeezyClientHelper;
import com.firstdata.payeezy.models.transaction.Ach;
import com.firstdata.payeezy.models.transaction.PayeezyResponse;
import com.firstdata.payeezy.models.transaction.TransactionRequest;
import com.firstdata.payeezy.models.transaction.TransactionResponse;

@Controller
public class TransactionController {

	@Autowired
	Environment env;
	@Autowired
	RestTemplate restTemplate;

	@RequestMapping("/transactions")
	public String transactions(Model model, HttpServletRequest request) {

		try {
			String num = request.getParameter("question");
			String amount = request.getParameter("amount");

			TransactionRequest tr = new TransactionRequest();
			tr.setTransactionType("purchase");
			tr.setPaymentMethod("ach");
			tr.setCurrency("USD");
			tr.setAch(new Ach());
			tr.getAch().setToken(num);
			tr.setAmount(amount);

			PayeezyClientHelper clientHelper = new PayeezyClientHelper(
					PropertyGetter.getProperties(env));

			PayeezyResponse payeezyResponse = clientHelper
					.doPrimaryTransaction(tr);
			System.out
					.println("Status Code:" + payeezyResponse.getStatusCode());
			System.out.println("Purchase: ");
			System.out.println(payeezyResponse.getResponseBody());

			JSONHelper jsonHelper = new JSONHelper();
			TransactionResponse response = jsonHelper.fromJson(
					payeezyResponse.getResponseBody(),
					TransactionResponse.class);

			String output = JsonUtil.getJSONObject(response);

			model.addAttribute("output", output);

			return "transactions";


		} catch (Exception e) {

			System.out.println(e.getMessage());

			model.addAttribute("output", e.getMessage());

			return "transactions";

		}
	}

}
