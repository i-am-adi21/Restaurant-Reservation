from flask import *
from database import *
import qrcode
import uuid

restaurant=Blueprint('restaurant',__name__)

@restaurant.route('/rest_home')
def rest_home():
	return render_template('rest_home.html')

@restaurant.route('/rest_manage_tables',methods=['get','post'])
def rest_manage_tables():
	data={}
	rid=session['restaurant_id']

	q="select * from categories"
	res=select(q)
	data['cat']=res

	q1="SELECT * FROM `tables` INNER JOIN `categories` USING(`category_id`) where restaurant_id='%s'"%(rid)
	res=select(q1)
	data['table']=res

	if 'action' in request.args:
		action=request.args['action']
		tid=request.args['tid']
	else:
		action=None
	if action=='update':
		q="select * from tables INNER JOIN `categories` USING(`category_id`) where table_id='%s'"%(tid)
		res=select(q)
		data['updatess']=res
	if action=='delete':
		q="delete from tables where table_id='%s'"%(tid)
		delete(q)
		flash('removed...')
		return redirect(url_for('restaurant.rest_manage_tables'))
	if action=='avail':
		q="update tables set table_availability='available' where table_id='%s'"%(tid)
		delete(q)
		
		return redirect(url_for('restaurant.rest_manage_tables'))
	if action=='notavail':
		q="update tables set table_availability='not available' where table_id='%s'"%(tid)
		delete(q)
		
		return redirect(url_for('restaurant.rest_manage_tables'))


	if 'submits' in request.form:
		cat=request.form['cat']
		table=request.form['table']
		seats=request.form['seats']
		# avail=request.form['avail']
		q="update `tables` set `category_id`='%s',`table_name`='%s',`number_of_seats`='%s' where table_id='%s'"%(cat,table,seats,tid)
		update(q)
		flash('successfully updated...')
		return redirect(url_for('restaurant.rest_manage_tables'))

	if 'submit' in request.form:
		cat=request.form['cat']
		table=request.form['table']
		seats=request.form['seats']
		# avail=request.form['avail']
		q="INSERT INTO `tables`(restaurant_id,`category_id`,`table_name`,`number_of_seats`,table_availability)VALUES('%s','%s','%s','%s','not available')"%(rid,cat,table,seats)
		id=insert(q)
		path = "static/qrcode/" + str(uuid.uuid4()) + ".png"
		img = qrcode.make(id)
		img.save(path)
		flash('success...')
		return redirect(url_for('restaurant.rest_manage_tables'))
	return render_template('rest_manage_tables.html',data=data)

@restaurant.route('/rest_manage_menu',methods=['get','post'])
def rest_manage_menu():
	data={}
	rid=session['restaurant_id']

	q="select * from menu where restaurant_id='%s'"%(rid)
	res=select(q)
	data['menu']=res
	if 'action' in request.args:
		action=request.args['action']
		mid=request.args['mid']
	else:
		action=None
	if action=='update':
		q="select * from menu where menu_id='%s'"%(mid)
		res=select(q)
		data['updatess']=res
	if action=='delete':
		q="delete from menu where menu_id='%s'"%(mid)
		delete(q)
		flash('removed...')
		return redirect(url_for('restaurant.rest_manage_menu'))
	if action=='avail':
		q="update menu set item_availability='available' where menu_id='%s'"%(mid)
		update(q)
		
		return redirect(url_for('restaurant.rest_manage_menu'))
	if action=='notavail':
		q="update menu set item_availability='not available' where menu_id='%s'"%(mid)
		update(q)
		
		return redirect(url_for('restaurant.rest_manage_menu'))

	if 'submits' in request.form:
		name=request.form['name']
		image=request.files['image']
		path="static/uploads/"+str(uuid.uuid4())+image.filename
		image.save(path)
		qnty=request.form['qnty']
		price=request.form['price']
		# avail=request.form['avail']

		q="update `menu` set `item_name`='%s',`item_image`='%s',`item_quantity`='%s',`item_price`='%s' where menu_id='%s'"%(name,path,qnty,price,mid)
		update(q)
		flash('successfully updated...')
		return redirect(url_for('restaurant.rest_manage_menu'))

	if 'submit' in request.form:
		name=request.form['name']
		image=request.files['image']
		path="static/uploads/"+str(uuid.uuid4())+image.filename
		image.save(path)
		qnty=request.form['qnty']
		price=request.form['price']
		# avail=request.form['avail']

		q="INSERT INTO `menu`(restaurant_id,`item_name`,`item_image`,`item_quantity`,`item_price`,`item_availability`)VALUES('%s','%s','%s','%s','%s','not available')"%(rid,name,path,qnty,price)
		insert(q)
		flash('success...')
		return redirect(url_for('restaurant.rest_manage_menu'))

	return render_template('rest_manage_menu.html',data=data)

@restaurant.route('/rest_manage_facilities',methods=['get','post'])
def rest_manage_facilities():
	data={}
	rid=session['restaurant_id']

	q="select * from facilities where restaurant_id='%s'"%(rid)
	res=select(q)
	data['facility']=res

	if 'action' in request.args:
		action=request.args['action']
		fid=request.args['fid']
	else:
		action=None
	if action=='update':
		q="select * from facilities where facility_id='%s'"%(fid)
		res=select(q)
		data['updatess']=res
		print(res)
	if action=='delete':
		q="delete from facilities where facility_id='%s'"%(fid)
		delete(q)
		flash('removed...')
		return redirect(url_for('restaurant.rest_manage_facilities'))
	if 'submits' in request.form:
		fname=request.form['fname']
		desc=request.form['desc']
		image=request.files['image']
		path="static/uploads"+str(uuid.uuid4())+image.filename
		image.save(path)

		q="UPDATE `facilities` set `facility_name`='%s',`facility_description`='%s',image='%s' where facility_id='%s'"%(fname,desc,path,fid)
		update(q)
		flash('successfully updated...')
		return redirect(url_for('restaurant.rest_manage_facilities'))


	if 'submit' in request.form:
		fname=request.form['fname']
		desc=request.form['desc']
		image=request.files['image']
		path="static/uploads"+str(uuid.uuid4())+image.filename
		image.save(path)
		q="INSERT INTO `facilities`(restaurant_id,`facility_name`,`facility_description`,image)VALUES('%s','%s','%s','%s')"%(rid,fname,desc,path)
		insert(q)
		flash('success...')
		return redirect(url_for('restaurant.rest_manage_facilities'))
	return render_template('rest_manage_facilities.html',data=data)


@restaurant.route('/rest_view_reservations',methods=['get','post'])
def rest_view_reservations():


	data={}
	rid=session['restaurant_id']
	q="SELECT *,CONCAT(`first_name`,' ',`last_name`) AS `name` FROM `reservations` INNER JOIN `tables` USING(`table_id`) INNER JOIN `customers` USING(`customer_id`)where restaurant_id='%s'"%(rid)
	res=select(q)
	data['reservation']=res
	return render_template('rest_view_reservations.html',data=data)

@restaurant.route('/rest_view_category')
def rest_view_category():
	cat_id=request.args['cat_id']
	data={}
	q="select * from categories where category_id='%s'"%(cat_id)
	res=select(q)
	data['cat']=res
	return render_template('rest_view_category.html',data=data)

@restaurant.route('/rest_view_customer_details')
def rest_view_customer_details():
	customer_id=request.args['customer_id']
	data={}
	q="select *,CONCAT(`first_name`,' ',`last_name`) AS `name` from customers where customer_id='%s'"%(customer_id)
	res=select(q)
	data['customer']=res
	return render_template('rest_view_customer_details.html',data=data)

@restaurant.route('/rest_view_food_ordered')
def rest_view_food_ordered():
	reservation_id=request.args['reservation_id']
	data={}
	q="SELECT * FROM `food_ordered` INNER JOIN `menu` USING(`menu_id`) WHERE `reservation_id`='%s'"%(reservation_id)
	res=select(q)
	data['order']=res
	return render_template('rest_view_food_ordered.html',data=data)

@restaurant.route('/rest_view_payment')
def rest_view_payment():
	
	data={}
	res_id=request.args['res_id']
	data['res_id']=res_id
	q="SELECT * FROM `payment` INNER JOIN `reservations` USING(`reservation_id`) where reservation_id='%s'"%(res_id)
	res=select(q)
	data['payment']=res

	if 'action' in request.args:
		action=request.args['action']
		rid=request.args['rid']
	else:
		action=None
	if action=='confirm':
		q="UPDATE `reservations` SET `reservation_status`='confirm' WHERE `reservation_id`='%s'"%(rid)
		update(q)
		flash('success...')
		return redirect(url_for('restaurant.rest_view_payment',res_id=res_id))
	return render_template('rest_view_payment.html',data=data)


@restaurant.route('/rest_view_rating')
def rest_view_rating():
	data={}
	rid=session['restaurant_id']
	
	q="SELECT *,CONCAT(`first_name`,' ',`last_name`)AS cname FROM `ratings` INNER JOIN `customers` USING(`customer_id`) WHERE `restaurant_id`='%s'"%(rid)
	res=select(q)
	data['rate']=res
	return render_template('rest_view_rating.html',data=data)