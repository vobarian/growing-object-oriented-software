# Growing Object-Oriented Software, Guided by Tests

## Auction Sniper example re-creation

The commit history of this repository presents a step-by-step re-creation of
the auction sniper example project from the book _Growing Object-Oriented Software, Guided by Tests_
by Steve Freeman and Nat Pryce.

## Features of this repository

* follows the book closely
* page number and description in each commit message
* uses code from the authors' repository to fill in parts omitted from the book
  (https://github.com/sf105/goos-code)
* changes are broken down into small, logical commits, even where the narrative
  in the book mentions multiple refactorings in a sentence
* commits in between writing and passing tests allow you to see the failure
  messages for instructional purposes
* Gradle is used for dependency management and build
* complete--includes all steps and code from chapters 10 through 19
 
## Notes

This repository aims to faithfully present the printed code. However there are
many minor inconsistencies in the printed code that would prevent it from
working verbatim. I consulted the authors' own repository
(https://github.com/sf105/goos-code) to resolve ambiguities and conflicts.
I liberally copied code from their repository to fill in portions that
were omitted from the book but mentioned in passing or implied by the
code shown. Since the authors' repository only shows the final form of the
code, sometimes it was necessary to make an educated guess about the
code's form at the point it's introduced in the narrative.

Although the book states that code should never be committed with failing
unit tests (p. 8), the purpose of this repository is instructional, so
there are many commits with failing tests so you can see the messages.
In a few commits, not all the code compiles--just enough to
run the tests in question.

Where a set of changes is spread across multiple pages, the page number in
the commit message is the last page on which code of that set appears.

## Openfire installation & configuration

The example requires a local XMPP server running. The book uses Openfire.
I used a more recent version than the book (4.0.3, dated August 2016). I
simply downloaded the ZIP file distribution, unzipped it, and ran
startup.jar from the command line. With the default configuration you should
be able to connect to the web admin console at localhost:9090. From there,
follow the guidance in the book on p. 89. In particular:

* During initial setup:
  * set host/server name to localhost
  * on the Database Settings screen select Embedded Database
* After initial setup: 
  * on the Users/Groups tab, create the 3 users specified in the book
  * under Server -> Server Settings -> Resource Policy, set Conflict Policy to Never kick
  * under Server -> Server Settings -> Offline Messages, select Drop
* _Optionally_ disable anonymous auth and unneeded services (for security nuts like me):
  * Server Manager tab
    * System Properties -> xmpp.auth.anonymous = false
  * Server Settings tab
    * Registration & Login -> disable inband account registration, change password, and anonymous login
    * Server to Server -> Plain-text (with STARTTLS) connections -> uncheck Enabled
    * External Component Settings -> "Plain-text (with STARTTLS) connections" and "Encrypted (legacy-mode) connections" uncheck
    * Connection Managers -> Disabled
    * HTTP Binding -> Disabled
    * File Transfer Settings -> Proxy Service = disabled
    * Search Service Properties -> Service Enabled = disabled

## Building & running

The project includes the Gradle wrapper so you do not need to install Gradle;
just invoke the gradlew script. (For *nix, make sure it is executable and
invoke it as `./gradlew`.)

To compile and run all tests from the command line:

    gradlew check

If the tests have already been run successfully and the code has not been changed,
Gradle will not re-run the tests when the command is executed repeatedly. If you
want to force the tests to re-run:

    gradlew cleanTest check

Most likely you'll want to import the code into your IDE. As the project
progresses, some dependencies are added, so remember to refresh the
dependencies in your IDE from the Gradle file when necessary.

## Troubleshooting

If something isn't working as you expected:

* Try a clean: `gradlew clean` (and clean the project in your IDE).
* If you get errors about missing classes, refresh your IDE
  project model from the Gradle file.
* If tests are failing or it won't compile, remember that some commits
  are meant to fail in this way. Look at the commit message
  and book narrative to figure out what is expected.
* End-to-end and some integration tests simulate user input. Do not
  disturb the keyboard or mouse while they're running.