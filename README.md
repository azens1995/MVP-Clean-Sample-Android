# MVP-Clean-Sample

## What
This is a simple experimental project that has been created following the [Clean architecture pattern](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) along with the [MVP](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) pattern for building the presentation layer.

The main issue was to understand how to split the application packages in features but at the same time keep the separation of concerns between the three layers and follow the correct dependency rule.

## The approach
I've decided to follow an approach where the project is splitted into three different modules:
- data (lib)
- domain (lib dependency on data)
- presentation (app dependent on domain) 

### Pros
1. **Forced separation of concerns:** Having separated modules forces the developer to build code that is isolated and reusable, it automatically defines the boundaries of each layer so you have to deal with stuff like exposing the api of a layer through an interface or defining different models for each layer so that you can store different data in relation of how you will use that model.
2. **Gradle helps with the dependency rule:** Dependency rule is really important when we talk about clean architecture, thanks to the different gradle configuration, you can specify the dependent for each module so you don't have to worry about that, you'll eventually get compilation errors if you don't follow the right direction.

### Cons
1. The only cons that i've found with this approach (i haven't proved it yet) is that it might need more flexibility when it comes to big project. You might want to conside splitting the project in feature-modules and having the three layers inside each feature-module or find other tricks to make this approach more scalable and flexible.
