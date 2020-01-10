package co.domainmodeling.chaper3

case class Lens[O, V] (
                        get: O => V,
                        set: (O, V) => O
                      )
object Lens {
  def compose[Outer, Inner, Value](
                                    outer: Lens[Outer, Inner],
                                    inner: Lens[Inner, Value]): Lens[Outer, Value] =
    Lens[Outer, Value] (
      get = obj => inner.get(outer.get(obj)),
      set = (obj, value) => outer.set(obj, inner.set(outer.get(obj), value))
    )
}