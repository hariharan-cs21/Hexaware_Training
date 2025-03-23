use banking_system;
-- 1
select c.first_name,avg(a.balance) as average_of_each
from customers c
join accounts a
on a.customer_id=c.customer_id
group by c.first_name;

-- 2
select c.first_name,a.balance
from customers c
join accounts a
on c.customer_id=a.customer_id
order by a.balance desc limit 10;

-- 3
select sum(amount) AS total_deposits
from transactions
where transaction_type = 'deposit' 
and transaction_date = '2025-03-19 11:05:07';

-- 4

select t.transaction_id, t.account_id, t.amount, t.transaction_type, t.transaction_date, a.account_type
from transactions t
join accounts a on t.account_id = a.account_id;

-- 5
SELECT c.customer_id, c.first_name, c.last_name, a.account_id, a.account_type, a.balance
FROM customers c
JOIN accounts a ON c.customer_id = a.customer_id;

-- 6
SELECT t.transaction_id, t.account_id, t.amount, t.transaction_type, t.transaction_date, 
       c.customer_id, c.first_name, c.last_name
FROM transactions t
JOIN accounts a ON t.account_id = a.account_id
JOIN customers c ON a.customer_id = c.customer_id
WHERE t.account_id = '3';

-- 7
SELECT c.customer_id, c.first_name, c.last_name, COUNT(a.account_id) AS num_accounts
FROM customers c
JOIN accounts a ON c.customer_id = a.customer_id
GROUP BY c.customer_id, c.first_name, c.last_name
HAVING COUNT(a.account_id) > 1;

-- 9
SELECT account_id, AVG(balance) AS average_daily_balance
FROM accounts
WHERE balance BETWEEN '2025-03-19 11:05:07' AND '2025-03-20 11:05:07'
GROUP BY account_id;

-- 10
SELECT account_type, SUM(balance) AS total_balance
FROM accounts
GROUP BY account_type;

-- 11
SELECT account_id, COUNT(transaction_id) AS transaction_count
FROM transactions
GROUP BY account_id
ORDER BY transaction_count DESC;

-- 12
SELECT c.customer_id, c.first_name, c.last_name, a.account_type, SUM(a.balance) AS total_balance
FROM customers c
JOIN accounts a ON c.customer_id = a.customer_id
GROUP BY c.customer_id, c.first_name, c.last_name, a.account_type
HAVING SUM(a.balance) > 100000; 

-- 13
SELECT transaction_id, account_id, amount, transaction_date, COUNT(*) AS duplicate_count
FROM transactions
GROUP BY transaction_id
HAVING COUNT(*) > 1;






