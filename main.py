from os import path


def select_absolute_paths() -> list:
    _absolute_paths = list()

    while len(_absolute_paths) < 2:
        _phrase = 'entrada' if len(_absolute_paths) < 1 else 'saida'
        _aux = str(input(f'Favor informar o local do arquivo de {_phrase}: '))

        if not path.isfile(_aux):
            print('Diretório informado não foi localizado!')
            continue

        _absolute_paths.append(_aux)

    return _absolute_paths

if __name__ == "__main__":
    print(select_absolute_paths())