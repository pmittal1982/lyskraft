select * from callback_request;
select * from mtp_user order by created_date desc;
select * from sku;
select * from one_time_password order by created_date desc;
select * from enquiry order by created_date desc;
select * from enquiry_aud;
select * from enquiry_item;
select * from item;
select * from company order by created_date desc;
select * from country;
select * from enquiry_country;
select * from company_buy_interest;
select * from company_sell_interest;
select * from company_aud;
select * from revinfo;
select * from kyc_document;
select * from company_kyc_document;
select * from quote order by created_date desc;
select * from quote_item;
select * from quote_aud;
select * from product;
select * from chat_message order by created_date desc;
select * from chat_body order by id desc;
select * from mtp_order;
select * from mtp_order_item;
select * from shanghai_stock_exchange_data order by created_date desc limit 10;

select enquiry_id, count(enquiry_id), max(created_date) as latestChatDate from chat_message where
 recipient_company_id = 2 or sender_company_id = 2 group by enquiry_id order by latestChatDate desc;