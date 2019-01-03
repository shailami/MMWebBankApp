<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<table>
		<tr>
			<th>Account Number</th>
			<th><a href="sortByName.mm">Holder Name</a></th>
			<th><a href="sortByBalance.mm">Account Balance</a></th>
			<th><a href="sortBySalary.mm">Salary</a></th>
			<th>Over Draft Limit</th>
			<th>Type Of Account</th>
		</tr>
		<jstl:if test="${requestScope.account!=null}">
			<tr>
				<td>${requestScope.account.bankAccount.accountNumber}</td>
				<td>${requestScope.account.bankAccount.accountHolderName }</td>
				<td>${requestScope.account.bankAccount.accountBalance}</td>
				<td>${requestScope.account.salary==true?"Yes":"No"}</td>
				<td>${"N/A"}</td>
				<td>${"Savings"}</td>
			</tr>
		</jstl:if>
		<jstl:if test="${!(requestScope.accounts==null)}">
			<jstl:forEach var="account" items="${requestScope.accounts}">
				<tr>
					<td>${account.bankAccount.accountNumber}</td>
					<td>${account.bankAccount.accountHolderName }</td>
					<td>${account.bankAccount.accountBalance}</td>
					<td>${account.salary==true?"Yes":"No"}</td>
					<td>${"N/A"}</td>
					<td>${"Savings"}</td>
				</tr>
			</jstl:forEach>
		</jstl:if>
		<jstl:if test="${!(requestScope.accountSingle==null)}">
		<form action="updateWithValues.mm">
			<tr>
				<td><input type="text" name="accountNumber"
					value="${accountSingle.bankAccount.accountNumber}" readonly></td>
				<td><input type="text" name="accountHolderName"
					value="${accountSingle.bankAccount.accountHolderName}"></td>
				<td><input type="text" name="accountBalance"
					value="${accountSingle.bankAccount.accountBalance}" readonly></td>
				<td><select name="isSalary"
					value="${accountSingle.salary==true?"Yes":"No"}">
						<option value="true">Yes</option>
						<option value="false">No</option>
				</select></td>
				<td><input type="text" name="odLimit" value="N/A" readonly></td>
				<td><input type="text" name="accountHolderName" value="Savings" readonly></td>
			</tr>
			<br>
			<tr>
			<td>
				<input type="submit" value="submit">
				<input type="reset" value="reset">
				</td>
			</tr>
			</form>
		</jstl:if>
	</table>
</body>
</html>