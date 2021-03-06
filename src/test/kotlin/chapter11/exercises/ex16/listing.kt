package chapter11.exercises.ex16

import arrow.Kind
import chapter11.Functor

interface Monad<F> : Functor<F> {
    fun <A> unit(a: A): Kind<F, A>
    fun <A, B> flatMap(fa: Kind<F, A>, f: (A) -> Kind<F, B>): Kind<F, B>
}

//tag::init1[]
data class Id<out A>(val a: A) : IdOf<A> {
    companion object {
        fun <A> unit(a: A): Id<A> = TODO()
    }

    fun <B> flatMap(f: (A) -> Id<B>): Id<B> = TODO()
    fun <B> map(f: (A) -> B): Id<B> = TODO()
}
//end::init1[]

class ForId private constructor() {
    companion object
}

typealias IdOf<A> = Kind<ForId, A>

fun <A> IdOf<A>.fix() = this as Id<A>

//tag::init2[]
val idMonad: Monad<ForId> = TODO()
//end::init2[]

fun main() {
    val id: Id<String> = idMonad.flatMap(Id("Hello, ")) { a: String ->
        idMonad.flatMap(Id("monad!")) { b: String ->
            Id(a + b)
        }
    }.fix()
}
