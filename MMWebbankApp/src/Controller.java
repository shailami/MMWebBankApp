import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.service.SavingsAccountService;
import com.moneymoney.account.service.SavingsAccountServiceImpl;
import com.moneymoney.exception.AccountNotFoundException;

@WebServlet("*.mm")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SavingsAccountService savingsAccountService = new SavingsAccountServiceImpl();
	private SavingsAccount savingsAccount;
	private SavingsAccount accountSingle;
	private RequestDispatcher dispatcher;
	// private int count = 0;
	boolean flag = false;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();

		switch (path) {
		case "/addsAccount.mm":
			response.sendRedirect("AddNewAccount.html");
			System.out.println("Welcome to MM Bank ");
			break;

		case "/removesAccount.mm":
			response.sendRedirect("remove.html");
			break;

		case "/updateAccount.mm":
			response.sendRedirect("update.html");
			break;

		case "/updateAccountExecute.mm":
			int accountNumber2 = Integer.parseInt(request
					.getParameter("accountNumber"));
			try {
				accountSingle = savingsAccountService
						.getAccountById(accountNumber2);
				request.setAttribute("accountSingle", accountSingle);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;

		case "/updateWithValues.mm":
			int accountNumber = Integer.parseInt(request
					.getParameter("accountNumber"));
			String accountHolderName = (request
					.getParameter("accountHolderName"));
			System.out.println(accountHolderName);
			String isSalary1 = request.getParameter("isSalary");
			try {
				savingsAccountService.updateAccount(accountNumber,
						"accountHolderName", accountHolderName);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e1) {
				e1.printStackTrace();
			}
			try {
				savingsAccountService.updateAccount(accountNumber, "isSalary",
						isSalary1);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e1) {
				e1.printStackTrace();
			}
			try {
				accountSingle = savingsAccountService
						.getAccountById(accountNumber);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			request.setAttribute("account", accountSingle);
			dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
			dispatcher.forward(request, response);
			break;

		case "/searchAccount.mm":
			response.sendRedirect("SearchForm.jsp");
			break;

		case "/search.mm":
			int accountNumberSearch = Integer.parseInt(request
					.getParameter("txtAccountNumber"));
			try {
				SavingsAccount account1 = savingsAccountService
						.getAccountById(accountNumberSearch);
				request.setAttribute("account", account1);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;

		case "/createNewAccount.mm":
			String name = request.getParameter("accountHolderName");
			System.out.println(name);
			double balance = Double.parseDouble(request
					.getParameter("accountBalance"));
			System.out.println(balance);
			boolean isSalary = Boolean.parseBoolean(request
					.getParameter("accountHolderName"));
			System.out.println(isSalary);
			createSavingsAccount(name, balance, isSalary);

			break;

		case "/deleteAccount.mm":
			int accountNumber3 = Integer.parseInt(request
					.getParameter("accountNumber"));
			System.out.println(accountNumber3);
			boolean result = deleteAccount(accountNumber3);
			if (result == true)
				System.out.println("account closed successfully!");
			else
				System.out.println("close account unsuccessfull");
			break;

		case "/deposit.mm":
			response.sendRedirect("deposit.html");
			break;

		case "/amountDeposited.mm":
			int accountNumberDeposit = Integer.parseInt((request
					.getParameter("accountNumber")));
			double amount = Double
					.parseDouble((request.getParameter("amount")));
			// System.out.println(+accountNumberDeposit+"   "+amount);
			deposit(accountNumberDeposit, amount);
			break;

		case "/getCurrentBalance.mm":
			response.sendRedirect("getCurrentBalance.html");
			break;

		case "/currentBalance.mm":
			// System.out.println("in current balance");
			int accountNumber1 = Integer.parseInt((request
					.getParameter("accountNumber")));
			// System.out.println(accountNumber1);
			double balance1 = getCurrentBalance(accountNumber1);
			System.out.println("Current Balance of account Number: "
					+ accountNumber1 + " is " + balance1 + " INR");
			break;

		case "/withdraw.mm":
			response.sendRedirect("withdraw.html");
			break;

		case "amountWithdrawn.mm":
			int accountNumberWithdraw = Integer.parseInt((request
					.getParameter("accountNumber")));
			double amount1 = Double
					.parseDouble((request.getParameter("amount")));
			// System.out.println(+accountNumberDeposit+"   "+amount);
			withdraw(accountNumberWithdraw, amount1);
			break;

		case "/fundTransfer.mm":
			response.sendRedirect("fundtransfer.html");
			break;

		case "/fundtransferexecute.mm":
			int senderAccountNumber = Integer.parseInt((request
					.getParameter("senderAccountNumber")));
			int receiverAccountNumber = Integer.parseInt((request
					.getParameter("receiverAccountNumber")));
			double amount2 = Double
					.parseDouble((request.getParameter("amount")));
			fundTransfer(senderAccountNumber, receiverAccountNumber, amount2);
			break;

		case "/getAll.mm":
			// System.out.println("in get all");
			try {
				List<SavingsAccount> accounts = savingsAccountService
						.getAllSavingsAccount();
				request.setAttribute("accounts", accounts);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case "/sortByName.mm":
			flag = !flag;
			List<SavingsAccount> inputList = new ArrayList<SavingsAccount>();
			try {
				inputList = savingsAccountService.getAllSavingsAccount();
				Collections.sort(inputList, new Comparator<SavingsAccount>() {

					@Override
					public int compare(SavingsAccount arg0, SavingsAccount arg1) {
						int result = arg0
								.getBankAccount()
								.getAccountHolderName()
								.compareTo(
										arg1.getBankAccount()
												.getAccountHolderName());
						// if (count % 2 != 0)
						if (flag == true)
							return result;
						else
							return -result;
					}
				});
				System.out.println("sort");
				request.setAttribute("accounts", inputList);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case "/sortByBalance.mm":
			try {
				// count++;
				flag = !flag;
				Collection<SavingsAccount> accounts = savingsAccountService
						.getAllSavingsAccount();

				Set<SavingsAccount> accountSet = new TreeSet<>(
						new Comparator<SavingsAccount>() {

							@Override
							public int compare(SavingsAccount arg0,
									SavingsAccount arg1) {
								int result = arg0.getBankAccount()
										.getAccountBalance() > arg1
										.getBankAccount().getAccountBalance() ? 1
										: -1;
								// if (count % 2 != 0)
								if (flag == true)
									return result;
								else
									return -result;
							}

						});
				accountSet.addAll(accounts);
				request.setAttribute("accounts", accountSet);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case "/sortBySalary.mm":
			List<SavingsAccount> inputList1 = new ArrayList<SavingsAccount>();
			try {
				flag = !flag;
				inputList1 = savingsAccountService.getAllSavingsAccount();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Collections.sort(inputList1, new Comparator<SavingsAccount>() {

				@Override
				public int compare(SavingsAccount arg0, SavingsAccount arg1) {
					int result = String.valueOf(arg0.isSalary()).equals("true") ? 1
							: -1;
					if (flag == true)
						return result;
					else
						return -result;
				}
			});
			request.setAttribute("accounts", inputList1);
			dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
			dispatcher.forward(request, response);
			break;

		case "/sortAccounts.mm":
			response.sendRedirect("getAll.mm");
			break;
		}
	}

	private void fundTransfer(int senderAccountNumber,
			int receiverAccountNumber, double amount) {
		SavingsAccount savingsAccount1;
		try {
			savingsAccount1 = savingsAccountService
					.getAccountById(senderAccountNumber);
			SavingsAccount savingsAccount2 = savingsAccountService
					.getAccountById(receiverAccountNumber);
			savingsAccountService.fundTransfer(savingsAccount1,
					savingsAccount2, amount);
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void withdraw(int accountNumberWithdraw, double amount1) {
		try {
			savingsAccount = savingsAccountService
					.getAccountById(accountNumberWithdraw);
			savingsAccountService.withdraw(savingsAccount, amount1);
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deposit(int accountNumberDeposit, double amount) {

		try {
			savingsAccount = savingsAccountService
					.getAccountById(accountNumberDeposit);
			savingsAccountService.deposit(savingsAccount, amount);
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private double getCurrentBalance(int accountNumber1) {
		SavingsAccount savingaccount = null;
		try {
			savingaccount = savingsAccountService
					.getAccountById(accountNumber1);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return savingaccount.getBankAccount().getAccountBalance();
	}

	private boolean deleteAccount(int accountNumber) {
		try {
			boolean result;
			result = savingsAccountService.deleteAccount(accountNumber) ? true
					: false;
			return result;
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private void createSavingsAccount(String name, double balance,
			boolean isSalary) {
		try {
			savingsAccountService.createNewAccount(name, balance, isSalary);
			System.out.println("account created");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
