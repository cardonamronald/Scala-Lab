package co.domainmodeling.chaper3

case class Lens[Object, Value] (
                        get: Object => Value,
                        set: (Object, Value) => Object
                      )
object Lens {
  def compose[Outer, Inner, Value](outer: Lens[Outer, Inner], inner: Lens[Inner, Value]): Lens[Outer, Value] =
    Lens[Outer, Value] (
      get = outer.get andThen inner.get,
      set = (obj, value) => outer.set(obj, inner.set(outer.get(obj), value))
    )
}