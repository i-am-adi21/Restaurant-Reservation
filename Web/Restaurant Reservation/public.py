from flask import *
from database import *

public=Blueprint('public',__name__)

@public.route('/')
def main_home():
	return render_template('main_home.html')


@public.route('/login',methods=['get','post'])
def login():


	if 'submit' in request.form:

		uname=request.form['uname']
		pwd=request.form['pwd']
		q="SELECT * FROM `login` WHERE (`username`='%s' AND `password`='%s')"%(uname,pwd)
		res=select(q)
		print(res)
		if res:
			session['login_id']=res[0]['login_id']
			if res[0]['user_type']=='admin':
				return redirect(url_for('admin.admin_home'))
			elif res[0]['user_type']=='restaurant':
				q="select restaurant_id from restaurant where login_id='%s'"%(session['login_id'])
				res=select(q)
				session['restaurant_id']=res[0]['restaurant_id']
				return redirect(url_for('restaurant.rest_home'))
	return render_template('/login.html')

@public.route('/rest_register',methods=['get','post'])
def rest_register():

	if 'submit' in request.form:

		name=request.form['name']
		place=request.form['place']
		lmark=request.form['lmark']
		street=request.form['street']
		pin=request.form['pin']
		phone=request.form['phone']
		email=request.form['email']
		uname=request.form['uname']
		pwd=request.form['pwd']
		
		q="INSERT INTO `login`(`username`,`password`,`user_type`)VALUES('%s','%s','pending')"%(uname,pwd)
		id=insert(q)
		q1="INSERT INTO `restaurant`(`login_id`,`name`,`place`,`landmark`,`street`,`pincode`,`phone`,`email`,`status`)VALUES('%s','%s','%s','%s','%s','%s','%s','%s','pending')"%(id,name,place,lmark,street,pin,phone,email)
		insert(q1)
		flash("Registered")
		return redirect(url_for('public.rest_register'))
	return render_template('/rest_register.html')