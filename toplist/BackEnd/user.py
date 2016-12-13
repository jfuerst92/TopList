import webapp2
from google.appengine.ext import ndb
import db_models
import json
import random
import logging

class User(webapp2.RequestHandler):
	def post(self):  #add user
		if 'application/json' not in self.request.accept:
			self.response.status = 406
			self.response.status_message = "Not acceptable, API only supports application/json"
			return
		new_user = db_models.User()
		#, default_value=None
		
		fName = self.request.get('fName')
		logging.info(fName)
		email = self.request.get('email')
		password = self.request.get('password')
		#email = self.request.get('email', default_value=None)
		upRates = 0
		dnRates = 0
		#age = self.request.get('age')
		gender = self.request.get('gender')
		city = self.request.get('city')
		state = self.request.get('state')
		#groups = self.request.get('groups[]', default_value=None)
		#image = self.request.get('image', default_value = None)
		"""
		if not fName or not email or not age or not gender or not city or not state:
			self.response.status = 400
			self.response.status_message = "Invalid request"
			return
		 """   
		#else:
		new_user.fName = fName
		new_user.email = email
		new_user.password = password
			#new_user.lName = lName
		new_user.upRates = upRates
		new_user.dnRates = dnRates
		new_user.gender = gender
		new_user.city = city
		new_user.state = state
		#new_user.age = age
			#new_user.image = image
		
		#if groups:
			#for group in groups:
				#new_user.groups.append(ndb.Key(db_models.Group, int(group)))
				
		key = new_user.put()
		out = new_user.to_dict()
		self.response.write(json.dumps(out))
	
	def get(self, **kwargs):   #get a specific user
		if 'application/json' not in self.request.accept:
			self.response.status = 406
			self.response.status_message = "Not acceptable, API only supports application/json"
			return 
		if 'id' in kwargs:
			out = ndb.Key(db_models.User, int(kwargs['id'])).get().to_dict()
			self.response.write(json.dumps(out))
		else:
			q = db_models.User.query()
			keys = q.fetch(keys_only=True)
			#results = { 'keys' : [x.id() for x in keys]}
			key = random.choice(keys)
			out = ndb.Key(db_models.User, int(key.id())).get().to_dict()
			self.response.write(json.dumps(out))
	
	def delete(self, **kwargs):
		if 'application/json' not in self.request.accept:
			self.response.status = 406
			self.response.status_message = "Not Acceptable, API only supports application/json MIME type"
			return
		if 'id' in kwargs:
			
			out = ndb.Key(db_models.User, int(kwargs['id'])).get()
			
			out.key.delete()
			self.response.write("User has been deleted")
		else:
			self.response.status = 404
			self.response.status_message = "ERROR:Cannot find user"

class userDel(webapp2.RequestHandler):		
	def post(self, **kwargs):
		if 'application/json' not in self.request.accept:
			self.response.status = 406
			self.response.status_message = "Not Acceptable, API only supports application/json MIME type"
			return
		if 'id' in kwargs:
			
			out = ndb.Key(db_models.User, int(kwargs['id'])).get()
			
			out.key.delete()
			self.response.write("User has been deleted")
		else:
			self.response.status = 404
			self.response.status_message = "ERROR:Cannot find user"
	
class UserImage(webapp2.RequestHandler):  #add an to user, create references in both entities
	def put(self, **kwargs):
		if 'application/json' not in self.request.accept:
			self.response.status = 406
			self.response.status_message = "Not acceptable, API only supports application/json"
			return
		if 'uid' in kwargs:
			#u = ndb.Key(db_models.User, int(kwargs['uid']))
			user = ndb.Key(db_models.User, int(kwargs['uid'])).get()
			
			if not user:
				self.response.status = 404
				self.response.status_message = "User Not Found"
				return
				
			image = self.request.get('image')
		#if 'pid' in kwargs:
			#image = ndb.Key(db_models.ImageData, int(kwargs['pid']))
			#i = ndb.Key(db_models.ImageData, int(kwargs['pid'])).get()
			if not image: 
				self.response.status = 404
				self.response.status_message = "Image key Not Found"
				return
		#if image not in user.images:
			user.image = image
			#user.images.append(image)
			user.put()
			#i.members.append(u)
			#g.put()
		self.response.write(json.dumps(user.to_dict()))
		return
		
class upRate(webapp2.RequestHandler):  #add an to user, create references in both entities
	def put(self, **kwargs):
		if 'application/json' not in self.request.accept:
			self.response.status = 406
			self.response.status_message = "Not acceptable, API only supports application/json"
			return
		if 'uid' in kwargs:
			#u = ndb.Key(db_models.User, int(kwargs['uid']))
			user = ndb.Key(db_models.User, int(kwargs['uid'])).get()
			
			if not user:
				self.response.status = 404
				self.response.status_message = "User Not Found"
				return
				
			upRates = self.request.get('upRates')
			
		#if 'pid' in kwargs:
			#image = ndb.Key(db_models.ImageData, int(kwargs['pid']))
			#i = ndb.Key(db_models.ImageData, int(kwargs['pid'])).get()
			if not upRates: 
				self.response.status = 404
				self.response.status_message = "No uprates"
				return
				
			
		#if image not in user.images:
			user.upRates = int(upRates)
			#user.images.append(image)
			user.put()
			#i.members.append(u)
			#g.put()
		self.response.write(json.dumps(user.to_dict()))
		return
		
class dnRate(webapp2.RequestHandler):  #add an to user, create references in both entities
	def put(self, **kwargs):
		if 'application/json' not in self.request.accept:
			self.response.status = 406
			self.response.status_message = "Not acceptable, API only supports application/json"
			return
		if 'uid' in kwargs:
			#u = ndb.Key(db_models.User, int(kwargs['uid']))
			user = ndb.Key(db_models.User, int(kwargs['uid'])).get()
			
			if not user:
				self.response.status = 404
				self.response.status_message = "User Not Found"
				return
				
			dnRates = self.request.get('dnRates')
		#if 'pid' in kwargs:
			#image = ndb.Key(db_models.ImageData, int(kwargs['pid']))
			#i = ndb.Key(db_models.ImageData, int(kwargs['pid'])).get()
			if not dnRates: 
				self.response.status = 404
				self.response.status_message = "No dnrates"
				return
		#if image not in user.images:
			user.dnRates = int(dnRates)
			#user.images.append(image)
			user.put()
			#i.members.append(u)
			#g.put()
		self.response.write(json.dumps(user.to_dict()))
		return		
		
class UserSearch(webapp2.RequestHandler):
	def post(self):
		if 'application/json' not in self.request.accept:
			self.response.status = 406
			self.response.status_message = "Not acceptable, API only supports application/json"
			return
		q = db_models.User.query()
		#if self.request.get('email', None):
		q = q.filter(db_models.User.email == self.request.get('email'))
		#if self.request.get('password', None):
		q = q.filter(db_models.User.password == self.request.get('password'))
		
		keys = q.fetch(keys_only=True)
		results = { 'keys' : [x.id() for x in keys]}
		match = 0
		#for x in keys:
			#match += 1
			#results = x
		#if (match > 0):
		self.response.write(json.dumps(results))
		#else:
			#results = "None"
			#self.response.write(results)
				
		
		
		
		
		
		
		
		
		
		
		