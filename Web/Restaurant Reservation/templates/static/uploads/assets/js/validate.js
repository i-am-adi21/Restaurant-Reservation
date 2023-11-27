jQuery.validator.addMethod("lettersonly", function(value, element) {
return this.optional(element) || /^[a-z\s]+$/i.test(value);
}, "Only alphabetical characters");
jQuery.validator.addMethod("noSpace", function(value, element) { 
    return value == '' || value.trim().length != 0;  
}, "No space please and don't leave it empty");
jQuery.validator.addMethod("customEmail", function(value, element) { 
  return this.optional( element ) || /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test( value ); 
}, "Please enter valid email address!");
var $registrationForm = $('#registration');
if($registrationForm.length){
  $registrationForm.validate({
      rules:{
          
          name: {
              required: true,
              lettersonly: true,
              noSpace: true
              
          },
          email: {
              required: true,
              customEmail: true
          },
          password: {
              required: true,
              minlength:6
          },
          cpassword: {
              required: true,
              equalTo: '#password'
          },
          address: {
              required: true
          },
          phone: {
              required: true,
              number: true,
              minlength: 10
          }
      },
      messages:{
          name: {
              required: 'Please enter a name!'
          },
          email: {
              required: 'Please enter email!',
              
              email: 'Please enter valid email!'
          },
          password: {
              required: 'Please enter password!',
              minlength: 'Enter a combination of atleast 6 characters!'
          },
          cpassword: {
              required: 'Please enter confirm password!',
              equalTo: 'Please enter same password!'
          },
          address: {
              required: 'Please enter address!'
          },
          phone: {
              required: 'Please enter mobile number!',
              minlength: 'Please enter valid mobile number!',
              number: 'Please enter valid mobile number!'

          }
      },
      
  });
}
var $loginForm = $('#login');
if($loginForm.length){
  $loginForm.validate({
      rules:{
        uname: {
              required: true,
              // customEmail: true
          },
          password: {
              required: true
          }
        },
        messages:{
          uname: {
              required: 'Please enter username!'
              
              // email: 'Please enter valid email!'
          },
          password: {
              required: 'Please enter password!'
          }
        },
          });
}
function isNumberKey(evt){ 
    var charCode = (evt.which) ? evt.which : event.keyCode 
    if (charCode > 31 && (charCode < 48 || charCode > 57)) 
        return false; 
    return true; 
} 