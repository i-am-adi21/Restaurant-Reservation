from flask import *
from database import *
import demjson
import uuid
import qrcode
api=Blueprint('api',__name__)

@api.route('/login',methods=['get','post'])
def login():
	data={}
	print("Haii")
	username=request.args['username']
	password=request.args['password']
	q="select * from login where username='%s' and password='%s'"%(username,password)
	res=select(q)
	print(res)
	if res:
		data['method']='login'
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
		data['method']='login'
	return demjson.encode(data)	

@api.route('/registration',methods=['get','post'])	
def register():
	data={}
	fname=request.args['fname']
	lname=request.args['lname']
	place=request.args['place']
	phone=request.args['phone']
	email=request.args['email']
	user=request.args['username']
	psw=request.args['password']
	q="select username,password from login where username='%s' and password='%s'" %(user,psw)
	result=select(q)
	if len(result)>0:
		data['status']="duplicate"
		data['method']="registration"
	else:
		q="insert into login values(null,'%s','%s','customer')"%(user,psw)
		id=insert(q)
		q="insert into customers values(null,'%s','%s','%s','%s','%s','%s')"%(id,fname,lname,place,phone,email)
		insert(q)
		data['status']="success"
		data['method']='registration'
	return demjson.encode(data)


@api.route('/View_hotels')
def View_hotels():
	data={}
	q="SELECT * FROM restaurant"
	res=select(q)
	if res:
		data['data']=res
		data['method']='View_hotels'
		data['status']='success'
	else:
		data['method']='failed'
	return demjson.encode(data)

@api.route('/View_hotel_facilities')
def View_hotel_facilities():
	data={}

	rid=request.args['rest_id']

	q="SELECT * FROM facilities where restaurant_id='%s'"%(rid)
	res=select(q)
	if res:
		data['data']=res
		data['method']='View_hotel_facilities'
		data['status']='success'
	else:
		data['method']='failed'
	return demjson.encode(data)



@api.route('/View_menu')
def View_menu():
	data={}
	rid=request.args['rest_id']
	q="SELECT * FROM menu where restaurant_id='%s'"%(rid)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
		data['method']='View_menu'
	else:
		data['method']='failed'
	return demjson.encode(data)



@api.route('/View_table_status')
def View_table_status():
	data={}
	rid=request.args['rest_id']
	q="SELECT * FROM `tables` INNER JOIN `categories` USING(category_id) where restaurant_id='%s' and table_availability='available'"%(rid)
	res=select(q)
	if res:
		data['data']=res
		data['method']='View_table_status'
		data['status']='success'
	else:
		data['method']='failed'
	return demjson.encode(data)



@api.route('/reserve_a_table')
def reserve_a_table():
	data={}
	logid=request.args['logid']
	tab_id=request.args['tab_id']
	q="SELECT * FROM `reservations` WHERE `table_id`='%s' AND `customer_id`='%s' AND `reservation_status`='pending'"%(tab_id,logid)
	res=select(q)
	if res:
		data['status']='request already send'
	else:
		q="INSERT INTO reservations VALUES(NULL,'%s',(SELECT customer_id FROM customers WHERE login_id='%s'),now(),'pending')" %(tab_id,logid)
		insert(q)
		data['status']='success'
	data['method']='reserve_a_table'
	return demjson.encode(data)



@api.route('/View_reserved_table')
def View_reserved_table():
	data={}
	logid=request.args['logid']
	q="SELECT * FROM reservations INNER JOIN `tables` USING(table_id) INNER JOIN `restaurant` USING(`restaurant_id`) INNER JOIN categories USING(category_id) WHERE customer_id=(SELECT customer_id FROM customers WHERE login_id='%s')" %(logid)
	print(q)
	res=select(q)
	if res:
		data['data']=res
		data['method']='View_reserved_table'
		data['status']='success'
	else:
		data['method']='failed'
	return demjson.encode(data)


@api.route('/cancel_booking')
def cancel_booking():
	data={}
	ress_id=request.args['ress_id']

	q="DELETE FROM `reservations` WHERE `reservation_id`='%s'"%(ress_id)
	delete(q)
	
	data['status']='success'
	data['method']='cancel_booking'
	return demjson.encode(data)




@api.route('/Confirm_reservation_by_paying_advance')
def Confirm_reservation_by_paying_advance():
	data={}
	logid=request.args['logid']
	ress_id=request.args['ress_id']
	amount=request.args['amount']
	q="INSERT INTO payment VALUES(NULL,'%s','%s',CURDATE())" %(ress_id,amount)
	insert(q)
	q="UPDATE reservations SET reservation_status='confirmed' WHERE reservation_id='%s'" %(ress_id)
	update(q)
	data['status']='success'
	data['method']='Confirm_reservation_by_paying_advance'
	return demjson.encode(data)



@api.route('/View_menu_to_order_foods')
def View_menu_to_order_foods():
	data={}
	rest_id=request.args['rest_id']
	q="SELECT * FROM `menu` WHERE item_availability='available' AND `restaurant_id`='%s'"%(rest_id)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
		data['method']='View_menu_to_order_foods'
	else:
		data['method']='failed'
	return demjson.encode(data)



@api.route('/Check_availability')
def Check_availability():
	data={}
	men_id=request.args['men_id']
	q="SELECT * FROM menu WHERE menu_id='%s'" %(men_id)
	res=select(q)
	if res:
		data['data']=res
		data['method']='Check_availability'
		data['status']='success'
	else:
		data['method']='failed'
	return demjson.encode(data)




@api.route('/Place_an_order')
def Place_an_order():
	data={}
	men_id=request.args['men_id']
	ress_id=request.args['ress_id']
	quantity=request.args['qty']
	q="INSERT INTO food_ordered VALUES(NULL,'%s','%s','%s')" %(ress_id,men_id,quantity)
	insert(q)
	q="SELECT item_quantity FROM menu WHERE menu_id='%s'" %(men_id)
	res=select(q)
	q=res[0]['item_quantity']
	newquantity=int(q)-int(quantity)
	if newquantity<=0:
		q="UPDATE menu SET item_quantity='0',item_availability='no' WHERE menu_id='%s'" %(men_id)
		update(q)
	else:
		q="UPDATE menu SET item_quantity='%s' Where menu_id='%s'" %(newquantity,men_id)
		update(q)
	data['status']='success'
	data['method']='Place_an_order'
	return demjson.encode(data)



@api.route('/View_feedback')
def View_feedback():
	data={}
	logid=request.args['logid']
	q="SELECT * FROM feedback WHERE customer_id=(SELECT customer_id from customers WHERE login_id='%s')" %(logid)
	res=select(q)
	print(res)
	if res:
		data['data']=res
		data['method']='View_feedback'
		data['status']='success'
	else:
		data['method']='failed'
	return demjson.encode(data)



@api.route('/addfeedback')
def addfeedback():
	data={}
	logid=request.args['logid']
	desc=request.args['desc']
	q="INSERT INTO feedback VALUES(NULL,(SELECT customer_id FROM customers WHERE login_id='%s'),'%s','pending',CURDATE())" %(logid,desc)
	insert(q)
	data['method']='addfeedback'
	data['status']='success'
	return demjson.encode(data)




@api.route('/view_rating',methods=['get','post'])
def view_rating():
	data = {}

	login_id=request.args['login_id']
	rest_id=request.args['rest_id']
	
	q=" SELECT * FROM `ratings` WHERE `restaurant_id`='%s' AND `customer_id`=(select customer_id from customers where login_id='%s')"%(rest_id,login_id)
	print(q)
	result=select(q)
	if result:
		data['status'] = 'success'
		data['data'] = result[0]['rate']
		data['data1'] = result[0]['review']
		
	else:
		data['status'] = 'failed'
	data['method'] = 'view_rating'
	return demjson.encode(data)

@api.route('/rate_restaurant',methods=['get','post'])
def rate_restaurant():

	data={}

	login_id=request.args['login_id']
	rest_id=request.args['rest_id']
	rating=request.args['rating']
	review=request.args['review']

	q="SELECT * FROM `ratings` WHERE `restaurant_id`='%s' AND `customer_id`=(select customer_id from customers where login_id='%s')"%(rest_id,login_id)
	res=select(q)
	if res:

		q="UPDATE `ratings` SET `rate`='%s',`review`='%s',`date_time`=NOW() WHERE `restaurant_id`='%s'"%(rating,review,rest_id)
		update(q)
		data['status'] = 'success'
	else:
		q="INSERT INTO `ratings`(`customer_id`,`restaurant_id`,`rate`,`review`,`date_time`)VALUES((select customer_id from customers where login_id='%s'),'%s','%s','%s',NOW())"%(login_id,rest_id,rating,review)
		id=insert(q)
		if id>0:
			data['status'] = 'success'
			
		else:
			data['status'] = 'failed'
	data['method'] = 'rate_restaurant'
	return demjson.encode(data)

@api.route('/view_qr_code/',methods=['get','post'])
def view_qr_code():
	data={}

	reservation_ids=request.args['reservation_ids']

	q="UPDATE reservations SET reservation_status='Booking Confirmed' WHERE reservation_id='%s'" %(reservation_ids)
	update(q)
	data['status']="success"
	data['method']="view_qr_code"
		
	return demjson.encode(data)


@api.route('/View_bookings/',methods=['get','post'])
def View_bookings():
	data={}

	login_id=request.args['login_id']

	q="SELECT * FROM `reservations` INNER JOIN `tables` USING(`table_id`) INNER JOIN `restaurant` USING(`restaurant_id`) WHERE `customer_id`=(SELECT `customer_id` FROM `customers` WHERE `login_id`='%s')"%(login_id)
	res=select(q)

	if res:
		data['data']=res
		data['method']="View_bookings"
		data['status']="success"
	else:
		data['status'] = 'failed'
	return demjson.encode(data)