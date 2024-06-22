import {MatSnackBar} from "@angular/material/snack-bar";

export const handleErrorAndShowSnackBar = (errorMessage: string, snackBar: MatSnackBar) => {
  snackBar.open(errorMessage, undefined, {
    duration: 3 * 1000,
    verticalPosition: 'top'
  })
  console.error(errorMessage);
}
