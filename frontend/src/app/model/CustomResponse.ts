export class CustomResponse<T> {
  private readonly _data: T | null = null;
  private readonly _error: string | null = null;

  constructor(data: T | null, error: string | null) {
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
