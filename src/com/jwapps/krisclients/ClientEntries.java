package com.jwapps.krisclients;


public class ClientEntries {

	// private variables
		int _id;
		String _name;
		String _address;
		String _phone;
		String _mobilePhone;
		String _email;
		
		String _massageDate;
		String _area;
		String _type;
		String _style;
		String _length;
		String _notes;
		

		// Empty constructor
		public ClientEntries() {

		}

		// constructor
		public ClientEntries(int id, String name, String address, String phone, String mobilePhone, String email) {
			
			this._id = id;
			this._name = name;
			this._address = address;
			this._phone = phone;
			this._mobilePhone = mobilePhone;
			this._email = email;
			
		}

		// constructor
		public ClientEntries(String name, String address, String phone, String mobilePhone, String email) {
			
			this._name = name;
			this._address = address;
			this._phone = phone;
			this._mobilePhone = mobilePhone;
			this._email = email;
			
		}
		
		// constructor
		public ClientEntries(String clientName, String notesStr, String massageDate){
			
			this._name = clientName;
			this._notes = notesStr;
			this._massageDate = massageDate;
			
		}
		
		// constructor
		public ClientEntries(String area, String type, String style, String length) {

			this._area = area;
			this._type = type;
			this._style = style;
			this._length = length;

		}
	
	
		// getting ID
		public int getID() {
			return this._id;
		}

		// setting id
		public void setID(int id) {
			this._id = id;
		}

		// getting name
		public String getName() {
			return this._name;
		}


		// setting name
		public void setName(String name) {
			this._name = name;
		}

		// getting address
		public String getAddress() {
			return this._address;
		}

		// setting address
		public void setAddress(String address) {
			this._address = address;
		}

		// getting phone
		public String getPhone() {
			return this._phone;
		}

		// setting phone
		public void setPhone(String phone) {
			this._phone = phone;
		}
		
		// getting mobile phone
		public String getMobilePhone() {
			return this._mobilePhone;
		}
		
		// Setting mobile phone
		public void setMobilePhone(String mobilePhone) {
			this._mobilePhone = mobilePhone;
		}
		
		//Getting email
		public String getEmail() {
			return this._email;
		}
		
		//Setting email
		public void setEmail(String email) {
			this._email = email;
		}
		
		// Getting area
		public String getArea() {
			return this._area;
		}

		// Setting area
		public void setArea(String area) {
			this._area = area;
		}
		
		// Getting type
		public String getType() {
			return this._type;
		}

		// Setting type
		public void setType(String type) {
			this._type = type;
		}
				
		// Getting style
		public String getStyle() {
			return this._style;
		}

		// Setting style
		public void setStyle(String style) {
			this._style = style;
		}
		
		// Getting area
		public String getLength() {
			return this._length;
		}

		// Setting area
		public void setLength(String length) {
			this._length = length;
		}
		
		// Getting notes
		public String getNotes() {
			return this._notes;
		}

		// Setting notes
		public void setNotes(String notesStr) {
			this._notes = notesStr;
		}
		
		// Getting date
		public String getMassageDate() {
			return this._massageDate;
		}

		// Setting date
		public void setDate(String massageDate) {
			this._massageDate = massageDate;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((_name == null) ? 0 : _name.hashCode());
			result = prime * result + ((_address == null) ? 0 : _address.hashCode());
			result = prime * result + ((_phone == null) ? 0 : _phone.hashCode());
			result = prime * result + ((_mobilePhone == null) ? 0 : _mobilePhone.hashCode());
			result = prime * result + ((_email == null) ? 0 : _email.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ClientEntries other = (ClientEntries) obj;
			if (_name == null) {
				if (other._name != null)
					return false;
			} else if (!_name.equals(other._name))
				return false;
			if (_address == null) {
				if (other._address != null)
					return false;
			} else if (!_address.equals(other._address))
				return false;
			if (_phone == null) {
				if (other._phone != null)
					return false;
			} else if (!_phone.equals(other._phone))
				return false;
			if (_mobilePhone == null) {
				if (other._mobilePhone != null)
					return false;
			} else if (!_mobilePhone.equals(other._mobilePhone))
				return false;
			if (_email == null) {
				if (other._email != null)
					return false;
			} else if (!_email.equals(other._email))
				return false;
			return true;
			
		}

	}
