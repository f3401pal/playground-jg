# Playground-jg
Playground and example project to demonstrate Android clean archetecture and other morden Android developemnt technologies. 

## Architecture
The project follows standard Android MVVM architecture. Data flows from Views(activities, fragments) -> ViewModels -> use cases -> repositories and vice versa. **The upstream and downstream data flow is separated.**

### Views
Activities and fragments that responsible for UI rendering, interactions and transition. View states should be purely **data-driven**. Means the view can be recreated by the given viwe state at any time. 
- views should react on view state data changes via observables
- user interactions via ViewModel
- could be tested with UI tests

### ViewModels
ViewModels keep view state data as observable stream and provide interface to interact with use cases.
- responsable for view state mangament based on view lifecycle (emit, replay, clear)
- data changes should **NOT** just stay at this layer but always flow downstream
- execute usecases on interactions from views layer
- maybe UI logics but no domain/business logic

### Use cases
Use cases implement domain/business logic. They can be combined and chained.
- should **NOT** have any state in this layer
- async operations should provide a observable as result
- provide access to repositories
- should have very high unit test coverage

### Repositorys
Repositorys provide interfaces to save or retrieve data from difference sources i.e. DB, API, etc.
- DB logic, API request and response mapping, etc
- could be singlton to provide in memory state


## Product Design
Since there is no standard approach to delete items according to Android MD, there are a couple approaches to implement the deleting transaction feature.
1. Having a delete action on each transaction with a confirmation dialog
2. Swipe the transaction item left to delete with undo snackbar
3. Having a toggle for deletion mode which show/hide a checkbox on each transaction.

I choose #1 because it is the most simply implementation.

## Limitations and Improvements
The DB query to get all transactions at once does not scale to a large amount of entries. A possible solution will be add paging support to only query transactions with a certain timeframe. However, the challenge come to observe the changes and new entries on previous pages. A possible solution will be query transactions for each day as a individual observable and zip them to a list of daily transaction observable.