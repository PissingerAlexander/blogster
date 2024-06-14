import {MatSnackBar} from "@angular/material/snack-bar";
import {HttpErrorResponse} from "@angular/common/http";

export const handleErrorAndShowSnackBar = (errorMessage: string, snackBar: MatSnackBar) => {
  snackBar.open(errorMessage, undefined, {
    duration: 3 * 1000,
    verticalPosition: 'top'
  })
  console.error(errorMessage);
}
