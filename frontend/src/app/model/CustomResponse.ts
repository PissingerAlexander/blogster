export class CustomResponse<T> {
  private readonly _data: T;
  private readonly _error: string;

  constructor(data: T, error: string) {
    this._data = data;
    this._error = error;
  }

  get data(): T | null {
    return this._data;
  }

  get error(): string | null {
    return this._error;
  }
}
