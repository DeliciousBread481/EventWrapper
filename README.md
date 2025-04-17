Event Wrapper
=============

Event Wrapper is a bridge that connects forge/fabric event system.

# Why use Event Wrapper?

Fed up different event system in different mod loader?

Developing a forge/fabric mod but can't without forge event?

HERE IS SOLUTION:

```java
@EventBusSubscriberWrapper
public SomeClass {
    
    @SubscriberEventWrapper
    private static void onHeal (LivingHealEventWrapper event) {
        //Some code on LivingEntity heal...
    }
    
    @SubscriberEventWrapper
    private static void onEffect(MobEffectEventWrapper event) {
        //Some code on LivingEntity add a effect...
    }
}
```

Just add `Wrapper` behind your forge event method. You can run it on both forge/fabric...

The modded community needs a Qin Shi Huang.

# Is it stable?

Sure.

It have the most compatibility on forge, also have compatibility on fabric(uses mixin).

# About LTS

We plan to support one `Main Version` each big minecraft version: `1.20.1`,`1.21.1`

# Our Principle

### Three E Principle:
* Easy to Use.
* Easy to Migrate.
* Easy to Exit.

Some libraries have caused serious migration disasters, so our principle is easy to use and easy to migrate out.

# Use in project
WIP