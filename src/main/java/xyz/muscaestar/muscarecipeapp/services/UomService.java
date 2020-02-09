package xyz.muscaestar.muscarecipeapp.services;

import xyz.muscaestar.muscarecipeapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UomService {

    Set<UnitOfMeasureCommand> listAllUoms();
}
