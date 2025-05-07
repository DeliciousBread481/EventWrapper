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
    public static void onHeal (LivingHealEventWrapper event) {
        //Some code on LivingEntity heal...
    }
    
    @SubscriberEventWrapper
    public static void onEffect(MobEffectEventWrapper event) {
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
```groovy
repositories {
    maven { 
        name = "jitpack"
        url = "https://jitpack.io"
    }
    //Or
    maven {
        name = "lounode"
        url = "https://maven.lounode.top/releases"
    }
}

//Choose one
modImplementation("com.github.Lounode.EventWrapper:eventwrapper-common:1.20.1-SNAPSHOT")
modImplementation("com.github.Lounode.EventWrapper:eventwrapper-forge:1.20.1-SNAPSHOT")
modImplementation("com.github.Lounode.EventWrapper:eventwrapper-fabric:1.20.1-SNAPSHOT")

//If use not jitpack:
//Choose one
modImplementation("io.github.lounode.eventwrapper:eventwrapper-common:1.20.1-1.0.0-SNAPSHOT")
modImplementation("io.github.lounode.eventwrapper:eventwrapper-forge:1.20.1-SNAPSHOT")
modImplementation("io.github.lounode.eventwrapper:eventwrapper-fabric:1.20.1-SNAPSHOT")

```
On forge it will automatic resolve event register

Fabric there need some manual call:

```java

@Override
public void onInitialize() {
    FabricLoader.getInstance().getModContainer("your_mod_id").ifPresent(mod -> {
        if (mod instanceof ModContainerImpl impl) {
            AutoEventSubscriberRegistryFabric.register(impl);
        }
    });
}

```