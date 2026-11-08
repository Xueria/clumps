package dev.xueria.clumps

import dev.xueria.clumps.core.MergeCompatibility
import dev.xueria.clumps.core.OrbSnapshot

class DefaultMergeCompatibility : MergeCompatibility {

    override fun canMerge(a: OrbSnapshot, b: OrbSnapshot): Boolean =
        a.entityId != b.entityId &&
                a.value > 0 &&
                b.value > 0 &&
                a.owner == b.owner &&
                a.bottle == b.bottle &&
                (a.age < 0) == (b.age < 0)

}
