# 2023-08-BAE-FS 
## Minimalist Book Manager API Take-Home Task

### 24-9.2023
I implemented tests for both service layer and controller for the delete by ID function.

I did added better functionality, but to the control layer - more info in the responses - handling stuff but now believe a better option would have been to throw exceptions in the service layer and fielded them in the control layer - either with global exception handlers or specific to each call.

i.e. business logic in the service layer.

Any unhandled exceptions in the repo layer, I assume, would then be caught in the control layer.

I will make these changes if I get chance, at a later date.

### 25-9-2023
Having done some investigation, it doesn't seem possible to throw exceptions from the service layer as the overrides are not available with exceptions being thrown. As I can't think of any way to pass the required information back to the control layer, I suspect that the control layers is the place for this logic though any input on this would be much appreciated. 