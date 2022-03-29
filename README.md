# Eventir
Eventir is an app that lets users create profiles, add events to their schedule, see other events in their area, and see what events their friends are attending.

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
[Description of your app]

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category: Social Media/Lifestyle**
- **Mobile: It is more convenient for the user to access this on their phone in order to view their schedule at a glance, as well as have reminders for what's next in their day.**
- **Story:**
- **Market: This is being marketed to users who want a one-stop palce for users to plan events, explore other events within the same genre, and see what similar events other users have gone to.**
- **Habit: Weekly**
- **Scope: User should be able to create/login to their profile, view events in their area, and plan events.**

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can create an account/login to their account
* User can view local events (Eventbrite API/Ticketmaster API, Google Maps API)
* User can view what events they have planned
* User can add new events to their schedule (Eventbrite API/TicketMaster API)
* User can click on an event to view detailed information (Eventbrite API/Ticketmaster API)
* User can change their account settings
* User can add friends/view their friends' profiles

**Optional Nice-to-have Stories**

* Detailed view links to the artist's music usic Spotify
* Filtered search for events (search events by genre, location, etc.)
* User can post comments about an event they attended to their profile

### 2. Screen Archetypes

* Login Screen
   * User can create an account/login to their account
* Registration Screen
   * User can create an account/login to their account
* Stream
   * User can view local events
* Detailed View
   * User can view what events they have planned
* Detailed View
   * User can click an event to view detailed information
* Profile
   * User can view their own/their friends' profile
* Settings
   * User can change their account settings
*

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Event stream screen tab.
* Profile tab.
* Settings tab.
* Event schedule tab.

**Flow Navigation** (Screen to Screen)

* click on event on stream screen/schedule screen/friend's profile.
   * goes to detail view of event.
* click on friend in friends list screen
   * goes to friend's profile.
* click on View Friends List button in profile screen.
   * goes to friends list screen
* click on Find Events button in Profile screen
   * goes to event stream screen
* click on Register button on in Login screen.
   * goes to registration screen
* click on logout in profile screen
   * goes to login screen
* click on login on login screen.
   * goes to schedule screen.
* click on event's location in event detail view
   * goes to map screen
## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="YOUR_WIREFRAME_IMAGE_URL" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
