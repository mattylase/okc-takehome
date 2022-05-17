## Matt Laser's Takehome App

### Overview
This app contains a minimal implementation of the given spec. There are two fragments, `SpecialBlendFragment` and `MatchFragment` that make up the main pieces of the UI. These fragments live in a `ViewPager2` owned by the `MainActivity`. Network access is done from the `Repository`, and coordination/updating of the data is done by the `TakehomeViewModel`. The `CandidateAdapter` contains all of the scrollable UI detailed in the spec.


## Libraries / Concepts
- Koin for dependency injection
- Ktor for networking
- MutableStateFlow used to publish updates as a stream
- mockk for unit tests
- coil for images

With some more free time (my current job & home life are a bit overwhelming right now :)), I would make some changes / additions:

- Add persistence / caching - currently all of the data is stored in-memory, including "Likes" and the fetched network data
- Refresh capabilities - ideally the app would have refresh and retry mechanisms built in, but given the small scope/limited personal resources, I'm only loading the network data on app launch
- Per the spec, the "Like" toggling is detailed under the "Special Blends" requirement, with those likes showing up on the "Match %" screen. This means that the items on the "Match %" screen are not clickable. In the future, I think it would make sense to make these clickable on both screens, but wasn't implemented due to the structure of the wording of the assignment.
- Most of the data classes representing the network data are using the network's format of `snake_case` instead of changing to `camelCase` during the deserialization process. This was just a time save for me, but in a production project would ideally be corrected.
- No paging support - pagination tokens are included in the network response, but they're going unused. As such, we're only ever looking at the first page.
- Image framing isn't great on large devices, but I felt it was good enough for the scope of the assignment
- Only some very simple unit testing is in place to state familiarity
- There isn't any UI automation testing included, but would be nice to have
