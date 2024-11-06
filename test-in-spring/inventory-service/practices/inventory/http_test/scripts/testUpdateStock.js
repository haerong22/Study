import {assertE2eDataEquals} from "./assertions"

const existingItemId = request.environment.get("existingItemId")
const newStock = parseInt(request.variables.get("newStock"))
assertE2eDataEquals(200, existingItemId, newStock)