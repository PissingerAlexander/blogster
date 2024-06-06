import {error} from "@angular/compiler-cli/src/transformers/util";

export class CustomRequest<T> {
  private readonly _response: T | null = null;
  private readonly _error: string | null = null;

  constructor(response: T | null, error: string | null) {
    this._response = response;
    this._error = error;
  }

  get response(): T | null {
    return this._response;
  }

  get error(): string | null {
    return this._error;
  }
}
