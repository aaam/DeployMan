/**************************************************************************************************
 *  ____  _  _  ____  _____  ____  __  __    __   ____  ____  ___    __   
 * (_  _)( \( )( ___)(  _  )(  _ \(  \/  )  /__\ (_  _)(_  _)/ __)  /__\  
 *  _)(_  )  (  )__)  )(_)(  )   / )    (  /(__)\  )(   _)(_( (__  /(__)\ 
 * (____)(_)\_)(__)  (_____)(_)\_)(_/\/\_)(__)(__)(__) (____)\___)(__)(__) 
 * 
 * Informatica PIM
 *
 * copyright: Informatica Corp. (c) 2003-2013.  All rights reserved.
 * 
 *************************************************************************************************/

package com.informatica.pim.cloud.repo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.informatica.pim.cloud.launch.formation.Formation;

import static com.informatica.pim.cloud.DeployMan.*;

/**
 * @author tuhrig
 */
public class FormationRepository implements IRepository, ILocaleRepository
{
  private LocaleRepository locale = new LocaleRepository();

  @Override
  public void printLocaleObjects()
  {
    File folder = getLocaleFolder();
    console.printFilesOfFolder( folder );
  }

  @Override
  public File getLocaleFolder()
  {
    return new File( this.locale.getLocation() + SLASH + FORMATION_FOLDER );
  }

  @Override
  public List< File > getLocaleFiles()
  {
    File localFolder = getLocaleFolder();
    return this.locale.getFilesOfFolder( localFolder );
  }

  public List< Formation > getFormations()
  {
    List< Formation > formations = new ArrayList();
    for ( File file : getLocaleFiles() )
    {
      Formation formation = Formation.read( file );
      formations.add( formation );
    }
    return formations;
  }

  public Formation getFormationByFileName( String fileName )
  {
    for ( Formation formation : getFormations() )
    {
      if ( formation.getFileName()
                    .equals( fileName ) )
      {
        return formation;
      }
    }

    return null;
  }

  @Override
  public boolean exists()
  {
    return existsLocale();
  }

  @Override
  public boolean existsLocale()
  {
    return getLocaleFolder().exists();
  }

  @Override
  public void init()
  {
    initLocale();
  }

  @Override
  public void initLocale()
  {
    if ( exists() )
      console.write( "Locale formation repository already exists (skip)" ); //$NON-NLS-1$

    else
    {
      String repoName = getUserProperty( REPO_LOCALE );
      this.locale.createLocalFolder( repoName, FORMATION_FOLDER );
      console.write( "Create locale folder '" + IMAGE_FOLDER + "' in " + repoName ); //$NON-NLS-1$ //$NON-NLS-2$
    }
  }

  @Override
  public void printInfo()
  {
    printLocaleObjects();
  }
}