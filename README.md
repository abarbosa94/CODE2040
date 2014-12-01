CODE2040
========

CODE2040 API Challenge Documentation

================================
Registration
================================

To get started, you’re first going to connect to the registration endpoint. It lives here:

http://challenge.code2040.org/api/register

The registration endpoint expects a JSON dictionary with two keys, email and github. This JSON should be sent in the body of your HTTP request.

For email, pass in a string with the same email address that you used to enroll with CODE2040. For github, pass in the URL of the repository you created in the last step.

The endpoint is going to return a string -- this “token” will uniquely identify you. You’ll use this token to complete the rest of the challenge, so make sure your code holds onto it.

//Hint: HTTP has a few types of “methods.” The registration endpoint is going to be expecting you to use POST to send your JSON.

Now it’s time for the challenge.

================================
Stage I: Reverse a string
================================

Once you’re registered, it’s time to get started on the challenges.

The first one is straightforward. You’re going to reverse a string.

That is, if the API says “cupcake,” you’re going to send back “ekacpuc.”

POST a JSON dictionary with the key token and your previous token value to this endpoint:

http://challenge.code2040.org/api/getstring

The getstring endpoint will return a string that your code should then reverse, as in the example above.

Once that string is reversed, send it back to us. POST some JSON to:

http://challenge.code2040.org/api/validatestring

Use the key token for your token.
Use the key string for your reversed string.

//Hint: There’s more than one way to skin a cat. However you reverse the string, all that matters to the API is that it’s flipped around accurately. That said, many libraries can do this work for you with very little code. There’s no shame in doing it the easy way -- if you can figure out how.

================================
Stage II: Needle in a haystack
================================

Next, let’s check your skills for working with collections.

We’re going to send you a dictionary with two values and keys. The first value, needle, is a string. The second value, haystack, is an array of strings. You’re going to tell the API where the needle is in the array.

Grab that dictionary from here, again by POSTing your token:

http://challenge.code2040.org/api/haystack

Locate the needle in the haystack array. You’re going to send back the position, or “index,” of the needle string. The API expects indexes to start counting at 0.

POST your results to:

http://challenge.code2040.org/api/validateneedle

Use the key token for your token.
Use the key needle for the integer representing where the needle was in the array.

//Hint: You’ll probably use a loop to solve this one.

================================
Stage III: Prefix
================================

Great job -- but we’re not done with collections.

In this challenge, the API is going to give you another dictionary. The first value, prefix, is a string. The second value, array, is an array of strings. Your job is to return an array containing only the strings that do not start with that prefix.

POST your token here:

http://challenge.code2040.org/api/prefix

Once you’ve built your array, POST a dictionary here:

http://challenge.code2040.org/api/validateprefix

Use the key token for your token.
Use the key array for your array.

//Hint: You’ll need a little string-fu to complete this challenge. But rest assured: comparing the beginnings of strings is a common task. Your platform’s standard libraries might even have some code to help you do this.

================================
Stage IV: The dating game
================================

Great job so far. The last challenge is a little different. You’re going to work with dates and times.

The API will again give you a dictionary. The value for datestamp is a string, formatted as an ISO 8601 datestamp. The value for interval is a number of seconds.

You’re going to add the interval to the date, then return the resulting date to the API. POST your token here:

http://challenge.code2040.org/api/time

Then POST a dictionary with your results here:

http://challenge.code2040.org/api/validatetime

Use the key token for your token.
Use the key datestamp for an ISO 8601 datestamp string.

//Hints:
Make sure your datestamp is formatted the same way as the one the API gives you.

Dates are hard! Don’t feel badly if you’re scratching your head on this one. Most platforms have libraries to help with date and time tasks. Don’t be afraid of using one to solve this challenge.

================================
Show your work
================================

Way to go! Now it’s time to show us how you did. Push your code up to your Github repository. Feel free to add comments in the code if you’re especially proud of how you solved a problem, or to tell us about anything you learned in the process of completing the challenge.

Again, don’t worry about being perfect. We just want to be able to show prospective employers that you can write your own code to solve problems.

To check your grades on the API challenge, POST a dictionary with your token to:

http://challenge.code2040.org/api/status

