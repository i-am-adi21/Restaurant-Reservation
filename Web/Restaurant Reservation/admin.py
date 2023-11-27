from flask import *
from database import *
import uuid


admin=Blueprint('admin',__name__)

@admin.route('/admin_home')
def admin_home():
	return render_template('admin_home.html')

@admin.route('/admin_view_rest')
def admin_view_rest():

	data={}
	q="SELECT * FROM `restaurant`"
	res=select(q)
	data['rest']=res

	if 'action' in request.args:
		action=request.args['action']
		ids=request.args['ids']
	else:
		action=None
	if action=='accept':
		q="UPDATE `restaurant` SET `status`='accepted' WHERE `login_id`='%s'"%(ids)
		update(q)
		q1="UPDATE `login` SET `user_type`='restaurant' WHERE `login_id`='%s'"%(ids)
		update(q1)
		flash('accepted')
		return redirect(url_for('admin.admin_view_rest'))

	if action=='reject':
		q="UPDATE `restaurant` SET `status`='rejected' WHERE `login_id`='%s'"%(ids)
		update(q)
		q1="UPDATE `login` SET `user_type`='rejected' WHERE `login_id`='%s'"%(ids)
		update(q1)
		flash('rejected')
		return redirect(url_for('admin.admin_view_rest'))
	return render_template('admin_view_rest.html',data=data)

@admin.route('/admin_view_hotel_menu',methods=['get','post'])
def admin_view_hotel_menu():
	data={}
	q="select * from menu inner join restaurant using(restaurant_id)"
	res=select(q)
	data['menu']=res
	
	return render_template('admin_view_hotel_menu.html',data=data)


@admin.route('/admin_view_table_availability',methods=['get','post'])
def admin_view_table_availability():
	data={}

	q1="SELECT * FROM `tables` INNER JOIN `categories` USING(`category_id`) inner join restaurant using(restaurant_id)"
	res=select(q1)
	data['table']=res

	
	return render_template('admin_view_table_availability.html',data=data)

@admin.route('/admin_manage_reservation_categories',methods=['get','post'])
def admin_manage_reservation_categories():
	data={}
	q="select * from categories"
	res=select(q)
	data['cat']=res

	if 'action' in request.args:
		action=request.args['action']
		cid=request.args['cid']
	else:
		action=None
	if action=='update':
		q="select * from categories where category_id='%s'"%(cid)
		res=select(q)
		data['updatess']=res
		print(res)
	if action=='delete':
		q="delete from categories where category_id='%s'"%(cid)
		delete(q)
		flash('removed...')
		return redirect(url_for('admin.admin_manage_reservation_categories'))
	if 'submits' in request.form:
		cname=request.form['cname']
		desc=request.form['desc']
		q="UPDATE `categories` set `category_name`='%s',`category_description`='%s' where category_id='%s'"%(cname,desc,cid)
		update(q)
		flash('successfully updated...')
		return redirect(url_for('admin.admin_manage_reservation_categories'))


	if 'submit' in request.form:
		cname=request.form['cname']
		desc=request.form['desc']
		q="INSERT INTO `categories`(`category_name`,`category_description`)VALUES('%s','%s')"%(cname,desc)
		insert(q)
		flash('success...')
		return redirect(url_for('admin.admin_manage_reservation_categories'))

	return render_template('admin_manage_reservation_categories.html',data=data)


@admin.route('/admin_view_hotel_facilities',methods=['get','post'])
def admin_view_hotel_facilities():
	data={}
	q="select * from facilities inner join restaurant using(restaurant_id)"
	res=select(q)
	data['facility']=res

	
	return render_template('admin_view_hotel_facilities.html',data=data)

@admin.route('/admin_view_reservations',methods=['get','post'])
def admin_view_reservations():

	data={}
	q="SELECT *,CONCAT(`first_name`,' ',`last_name`) AS `name` FROM `reservations` INNER JOIN `tables` USING(`table_id`) INNER JOIN `customers` USING(`customer_id`)"
	res=select(q)
	data['reservation']=res
	return render_template('admin_view_reservations.html',data=data)

@admin.route('/admin_view_category')
def admin_view_category():
	cat_id=request.args['cat_id']
	data={}
	q="select * from categories where category_id='%s'"%(cat_id)
	res=select(q)
	data['cat']=res
	return render_template('admin_view_category.html',data=data)
@admin.route('/admin_view_customer_details')
def admin_view_customer_details():
	customer_id=request.args['customer_id']
	data={}
	q="select *,CONCAT(`first_name`,' ',`last_name`) AS `name` from customers where customer_id='%s'"%(customer_id)
	res=select(q)
	data['customer']=res
	return render_template('admin_view_customer_details.html',data=data)

@admin.route('/admin_view_food_ordered')
def admin_view_food_ordered():
	reservation_id=request.args['reservation_id']
	data={}
	q="SELECT * FROM `food_ordered` INNER JOIN `menu` USING(`menu_id`) WHERE `reservation_id`='%s'"%(reservation_id)
	res=select(q)
	data['order']=res
	return render_template('admin_view_food_ordered.html',data=data)

@admin.route('/admin_view_payment')
def admin_view_payment():
	
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
		return redirect(url_for('admin.admin_view_payment',res_id=res_id))
	return render_template('admin_view_payment.html',data=data)




@admin.route('/admin_view_feedback',methods=['get','post'])
def admin_view_feedback():
	data={}
	q="SELECT *,CONCAT(`first_name`,' ',`last_name`)AS `name` FROM `feedback` INNER JOIN `customers` USING(`customer_id`)"
	res=select(q)
	data['feedback']=res
	
	j=0
	for i in range(1,len(res)+1):
		print('submit'+str(i))
		if 'submit'+str(i) in request.form:
			reply=request.form['reply'+str(i)]
			print(reply)
			print(j)
			print(res[j]['feedback_id'])
			q="UPDATE `feedback` SET `reply_descr`='%s' WHERE `feedback_id`='%s'" %(reply,res[j]['feedback_id'])
			print(q)
			update(q)
			flash("success")
			return redirect(url_for('admin.admin_view_feedback')) 	
		j=j+1
	return render_template('admin_view_feedback.html',data=data)
