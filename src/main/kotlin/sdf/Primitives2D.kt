package geomx.sdf

import geomx.transform.Basis2D
import geomx.transform.Vector2D
import geomx.transform.transform
import kotlin.math.*

public fun circle(p: Vector2D, r: Double): Double {
    return p.length() - r
}

public fun box(p: Vector2D, b: Vector2D): Double {
    val d = abs(p) - b
    return max(d, 0.0).length() + min(max(d.x, d.y), 0.0)
}

public fun boxRound(p: Vector2D, b: Vector2D, r: Double): Double {
    val q = abs(p) - b + r
    return min(max(q.x, q.y), 0.0) + max(q, 0.0).length() - r
}

public fun boxOriented(p: Vector2D, a: Vector2D, b: Vector2D, th: Double): Double {
    val l = (b - a).length()
    val d = (b - a) / l
    val q = abs(Basis2D(d.x, -d.y, d.y, d.x) transform (p - (a + b) * 0.5)) - Vector2D(l, th) * 0.5
    return max(q, 0.0).length() + min(max(q.x, q.y), 0.0)
}

public fun segment(p: Vector2D, a: Vector2D, b: Vector2D): Double {
    val pa = p - a
    val ba = b - a
    val h = ((pa dot ba) / (ba dot ba)).coerceIn(0.0, 1.0)
    return (pa - ba * h).length()
}

public fun rhombus(p: Vector2D, b: Vector2D): Double {
    val p1 = abs(p)
    val h = ((b - p1 * 2.0 ndot b) / (b dot b)).coerceIn(-1.0, 1.0)
    val d = (p1 - b * 0.5 * Vector2D(1.0 - h, 1.0 + h)).length()
    return d * sign(p1.x * b.y + p1.y * b.x - b.x * b.y)
}

public fun trapezoid(p: Vector2D, r1: Double, r2: Double, he: Double): Double {
    val k1 = Vector2D(r2, he)
    val k2 = Vector2D(r2 - r1, 2.0 * he)
    val p1 = Vector2D(abs(p.x), p.y)
    val ca = Vector2D(p1.x - min(p1.x, if (p1.y < 0.0) r1 else r2), abs(p1.y) - he)
    val cb = p1 - k1 + k2 * ((k1 - p1 dot k2) / (k2 dot k2)).coerceIn(0.0, 1.0)
    val s = if (cb.x < 0.0 && ca.y < 0.0) -1.0 else 1.0
    return s * sqrt(min(ca dot ca, cb dot cb))
}

public fun parallelogram(p: Vector2D, wi: Double, he: Double, sk: Double): Double {
    val e = Vector2D(sk, he)
    var p1 = if (p.y < 0.0) -p else p
    var w = p1 - e
    w = Vector2D(w.x - w.x.coerceIn(-wi, wi), w.y)
    var d = Vector2D(w dot w, -w.y)
    val s = p1.x * e.y - p1.y * e.x
    p1 = if (s < 0.0) -p1 else p1
    var v = p1 - Vector2D(wi, 0.0)
    v -= e * ((v dot e) / (e dot e)).coerceIn(-1.0, 1.0)
    d = min(d, Vector2D(v dot v, wi * he - abs(s)))
    return sqrt(d.x) * sign(-d.y)
}

public fun triangleEquilateral(p: Vector2D, r: Double): Double {
    val k = sqrt(3.0)
    var p1 = Vector2D(abs(p.x) - r, p.y + r / k)
    if (p1.x + k * p1.y > 0.0) p1 = Vector2D(p1.x - k * p1.y, -k * p1.x - p1.y) / 2.0
    p1 = Vector2D(p1.x - p1.x.coerceIn(-2.0 * r, 0.0), p1.y)
    return -p1.length() * sign(p1.y)
}

// TODO: Isosceles Triangle https://iquilezles.org/articles/distfunctions2d/
