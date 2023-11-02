---
layout: page
title: User Guide
---

FriendBook is a **desktop app for managing friend contacts and plan details, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, FriendBook can get your plans management done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `friendbook.jar` from here (link coming soon).

1. Copy the file to the folder you want to use as the _home folder_ for your FriendBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar friendbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list-friend` : Lists all contacts.

   * `add-friend n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the FriendBook.

   * `add-plan n/Meeting d/2023-11-01-10:00 f/John Doe` : Adds a plan named `Meeting` with `John Doe` to the FriendBook

   * `delete-friend 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add-friend n/NAME`, `NAME` is a parameter which can be used as `add-friend n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Adding a person: `add-friend`

Adds a person to the friends list.

Format: `add-friend n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS`

Examples:
* `add-friend n/John Doe p/98321234 e/johnd@example.com a/Thomson Avenue`
* `add-friend n/Jack Ma p/92839102 e/jack.ma@yahoo.com a/Sixth Avenue`

Arguments:
- `NAME`, `PHONE_NUMBER`, `EMAIL`, `ADDRESS` are all required fields.

Successful Output:

`New friend added: NAME, PHONE_NUMBER, EMAIL, ADDRESS`

Unsuccessful Output:

- Invalid command: `Invalid command.` + help message with list of all commands

- Empty name: `Names should only contain alphanumeric characters and spaces, and it should not be blank.`

- Non-numeric phone number: `Phone numbers should only contain numbers, and it should be at least 3 digits long`

- Invalid email format: `Emails should be of the format local-part@domain...`

### Editing a Friend's Information: `edit-friend`

Edits an existing friend’s information in the friends list at the specified index.

Format: `edit-friend INDEX n/NAME p/PHONE e/EMAIL a/ADDRESS`

Examples:
* `edit-friend 3 n/Jack Ma`
* `edit-friend 1 p/99990000`

Arguments:

- `NAME` must be a STRING, `PHONE` must be an INT, and `EMAIL` must be a STRING

- An `INDEX` must be provided

- `NAME`, `PHONE_NUMBER`, `EMAIL` and `ADDRESS` are optional but at least 1 must be given

Successful Output:

`Edited Friend: NAME, PHONE_NUMBER, EMAIL, ADDRESS`

Unsuccessful Output:

- Invalid command: `Invalid command.` + help message with list of all commands

- Invalid index is given: `The friend index provided is invalid`

- No optional arguments provided: `At least one field to edit must be provided.`

- Friend already exists: `This friend already exists in the FriendBook.`

### Removing a Friend : `delete-friend`

Removes an existing friend from the friends list.

Format: `delete-friend INDEX`

Examples:
* `delete-friend 1`
* `delete-friend 2`

Arguments:
- `NAME` must be a string

Successful Output:

`Deleted Friend: NAME, PHONE_NUMBER, EMAIL, ADDRESS`

Unsuccessful Output:

- Invalid command: `Invalid command.` + help message with list of all commands

- Empty index or index is not a integer: `Invalid command.
  delete-friend: Deletes the friend identified by the index number used in the displayed friend list.
  Parameters: INDEX (must be a positive integer)
  Example: delete-friend 1`

### Finding a friend : `find-friend`

Finds persons whose names contain any of the given keywords.

Format: `find-friend KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search). e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang` 

Examples:
* `find-friend John` returns `john` and `John Doe`
* `find-friend alex david` returns `Alex Yeoh`, `David Li`

Successful Output:
`COUNT_OF_FRIENDS friends listed!`

Unsuccessful Output:
- Invalid command: `Invalid command.` + help message with list of all commands
- Empty arguments: `Invalid syntax: Missing arguments.
  find-friend: Finds all friends whose names contain any of the specified keywords (case-insensitive) and displays them as a list with index numbers.
  Parameters: KEYWORD [MORE_KEYWORDS]...
  Example: find-friend alice bob charlie`

### Listing friends : `list-friend`
Shows a list of all friends in the FriendBook.

Format: `list-friend`

Successful Output:
`Listed all friends`

### Adding a plan : `add-plan`

Adds a plan and associates with involved friends.

Format: `add-plan n/PLAN_NAME d/DATE_TIME f/FRIEND_NAME`

Examples:
* `add-plan n/Project Meeting d/2023-10-23-10:00 f/Royden`

Arguments:

- `PLAN_NAME` must be a STRING, `DATE_TIME` must be in the YYYY-MM-DD-HH:MM format

- `FRIEND_NAME` is optional. If specified, `FRIEND_NAME` must be the name of an existing friend

Successful Output:

`New plan added: PLAN_NAME with FRIEND_NAME at DATE_TIME`

Unsuccessful Output:

- Invalid command: `Invalid command.` + help message with list of all commands

- Missing arguments: `Invalid syntax: Missing arguments.
  add-plan: Adds a plan to the FriendBook.
  Parameters: n/PLAN_NAME d/DATE_TIME f/FRIEND_NAME
  Example: add-plan n/Project Meeting d/2023-10-20-09:00 f/John Doe`

- Date-Time in wrong format: `Date-Time given is invalid. Date-Time must be in YYYY-MM-DD-HH:MM format`

- Date-Time in the past: `Date-Time given is invalid. Ensure that the Date-Time provided is not in the past.`

- Not a valid friend: `The friend does not exist in the FriendBook.`

### Editing a Plan's Information: `edit-plan`

Edits an existing plan’s information in the plans list at the specified index.

Format: `edit-plan INDEX n/PLAN_NAME d/DATE_TIME f/FRIEND_NAME`

Examples:
* `edit-plan 3 n/Dota 3`
* `edit-plan 1 d/2025-01-01-10:00`

Arguments:

- `PLAN_NAME` and `FRIEND_NAME` must be a STRING, `DATE_TIME` must be in the YYYY-MM-DD-HH:MM format
- An `INDEX` must be provided
- `PLAN_NAME`, `DATE_TIME`, `FRIEND_NAME` are optional but at least 1 must be given
- If specified, `FRIEND_NAME` must be the name of an existing friend

Successful Output:

`Edited Plan: PLAN_NAME with FRIEND_NAME at DATE_TIME`

Unsuccessful Output:

- Invalid command: `Invalid command.` + help message with list of all commands

- Invalid index is given: `The plan index provided is invalid`

- No optional arguments provided: `At least one field to edit must be provided.`

- Not a valid friend: `The friend does not exist in the FriendBook.`

### Marking a Plan as Completed : `complete-plan`

Marks the specified plan as completed.

Format: `complete-plan INDEX`

Examples:
* `complete-plan 4`

Arguments:

- `INDEX` must be an INTEGER

Successful Output:

`"[PLAN_NAME]" is marked as completed.`

Unsuccessful Output:

- Invalid command: `Invalid command.` + help message with list of all commands

- Empty index or index is not a positive non-zero integer: `Invalid command format!
  complete-plan: Marks the plan as completed identified by the index number used in the displayed plan list.
  Parameters: INDEX (must be a positive integer)
  Example: complete-plan 1

- Index given is out of bound: `No plans at given index.`

### Marking a Plan as Uncompleted : `uncomplete-plan`

Marks the specified plan as uncompleted.

Format: `uncomplete-plan INDEX`

Examples:
* `uncomplete-plan 4`

Arguments:

- `INDEX` must be an INTEGER

Successful Output:


`Completed Plan: [PLAN_NAME]; Date Time: [DATE_TIME]; Friend: [FRIEND_NAME]`

Unsuccessful Output:

- Invalid command: `Invalid command.` + help message with list of all commands

- Empty index or index is not a positive non-zero integer: `Invalid command format!
  uncomplete-plan: Marks the plan as not completed identified by the index number used in the displayed plan list.
  Parameters: INDEX (must be a positive integer)
  Example: uncomplete-plan 1

- Index given is out of bound: `No plans at given index.`

### Deleting a Plan : `delete-plan`

Deletes the specified plan.

Format: `delete-plan INDEX`

Examples:
* `delete-plan 4`

Arguments:

- `INDEX` must be an INTEGER
- `INDEX` must be defined within the size of current PlanList

Successful Output:

`Deleted Plan: PLAN_NAME with FRIEND_NAME at DATE_TIME`

Unsuccessful Output:

- Invalid command: `Invalid command.` + help message with list of all commands

- Invalid index is given: `The plan index provided is invalid`

### Finding a plan : `find-plan`

Find plans which is associated to a given friend.

Format: `find-plan FRIEND_NAME`

* `FRIEND_NAME` has to be the full name of the Person instance. e.g `find-plan Elijah` will throw an error if no Person has the full name `Elijah`

* The search is case-sensitive. e.g `hans` will not match `Hans`

* Only one full name will be taken in and searched (only one person's plans will be searched)

Examples:
* `find-plan John` returns all plans associated to `John`

Arguments:
- `FRIEND_NAME` must be the full name belonging to a Person saved in the FriendBook

Successful Output:
`COUNT_OF_PLANS plans listed!`

Unsuccessful Output:

- Invalid command: `Invalid command.` + help message with list of all commands

- Missing arguments: `Invalid syntax: Missing arguments.
  find-plan: Finds all plans which contains the specified friend.
  Parameters: FRIEND_NAME
  Example: find-plan Elijah Chia`

- Not a valid friend: `The friend does not exist in the FriendBook.`

### Listing plans : `list-plan`

Shows a list of all plans in the FriendBook.

Format: `list-plan`

Successful Output:
`Listed all plans`

Unsuccessful Output:

- Invalid command: `Invalid command.` + help message with list of all commands

### Clearing the Storage : `clear`
Clears the stored friends and plans on the `friendbook.json` file which is located in the `data` folder in FriendBook's home folder.

### Get the User Guide : `help`
Provides the FriendBook user guide's link.

### Exit FriendBook : `exit`
Closes the FriendBook Application.

### Saving the data

FriendBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

FriendBook data are saved automatically as a JSON file `[JAR file location]/data/friendbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, FriendBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous FriendBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**add-friend** | `add-friend n/NAME p/PHONE_NUMBER e/EMAIL` <br> e.g., `add-friend n/John Doe p/98321234 e/johnd@example.com`
**edit-friend** | `edit-friend INDEX n/NAME p/PHONE e/EMAIL`<br> e.g., `edit-friend 3 n/Jack Ma`
**delete-friend** | `delete-friend NAME` <br> e.g., `delete-friend John Doe`
**find-friend** | `find-friend KEYWORD [MORE_KEYWORDS]`<br> e.g., `find-friend Jack`
**list-friend** | `list-friend`
**add-plan** | `add-plan n/PLAN_NAME d/DATE_TIME f/FRIEND_NAME`<br> e.g.,`add-plan n/Project Meeting d/2023-10-23-10:00 f/Royden`
**edit-plan** | `edit-plan INDEX n/PLAN_NAME d/DATE_TIME f/FRIEND_NAME`<br> e.g., `edit-plan 1 d/2025-01-01-10:00`
**complete-plan** | `complete-plan INDEX`<br> e.g., `complete-plan 4`
**uncomplete-plan** | `uncomplete-plan INDEX`<br> e.g., `uncomplete-plan 4`
**delete-plan** | `delete-plan INDEX` <br> e.g., `delete-plan 4`
**find-plan** | `find-plan FRIEND_NAME` <br> e.g., `find-plan John`
**list-plan** | `list-plan`
