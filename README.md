Boomerang
===
Essential commands for Spigot-1.8.x - Version 3.1.1

## Currently working on

**Claim Protection**

* /claim \[name\] - Claim an area set my /pos1 and /pos2. If *name* not specified, set *name* to first home it encapsulates.
* /listclaims - List claims and their boundaries, claim block allowance, players allowed in each claim.
* /unclaim \[name\] - Remove claim. If *name* is not specified, remove claim from claim area currently standing in.
* /shareclaim \[name\] \<player\> - Share *named* claim with a player. If *name* is not specified, assume claim currently standing in.
* /giveclaim \[name\] \<player\> - Give *named* claim with a player. If *name* is not specified, assume claim currently standing in.
* /pos - Show current selection.
* /pos1 \[X Y Z\] - Set position 1. if *X, Y and Z* are not specified, assume block currently standing on.
* /pos2 \[X Y Z\] - Set position 2. if *X, Y and Z* are not specified, assume block currently standing on.
* /expand \<num\> \[direction\] - Expand selection by *num* blocks id *direction*. If *direction* is not specified, assume direction facing.
* /allow \[name\] \<player\> {use | break} - Allow a *player* to *use* or *break* in a claim. If *name* is not specified, assume claim currently standing in.
* /disallow \[name\] \<player\> {use | break} - Disallow a *player* to *use* or *break* in a claim. If *name* is not specified, assume claim currently standing in.

**Notes**

* 

If a chest is placed, set an automatic claim.

**Defaults**

* chest_claim_size
* max_claim_size
* max_claims
* hourly_claim_allowance

## Vote Listener

